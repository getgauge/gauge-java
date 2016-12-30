// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.connection;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.thoughtworks.gauge.ConceptInfo;
import com.thoughtworks.gauge.PluginNotInstalledException;
import com.thoughtworks.gauge.StepValue;
import gauge.messages.Api;
import gauge.messages.Spec;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GaugeConnection {

    private static final int GET_STEP_MSG_ID = 2;
    private static final int GET_INSTALLATION_ROOT_MSG_ID = 3;
    private static final int GET_STEP_VALUE_MSG_ID = 4;
    private static final int GET_LIB_PATH_MSG_ID = 5;
    private static final int GET_CONCEPT_MSG_ID = 6;
    private static final int PERFORM_REFACTORING_MSG_ID = 7;
    private static final int EXTRACT_CONCEPT_MSG_ID = 8;

    private final int port;
    private Socket gaugeSocket;

    public GaugeConnection(int port) {
        this.port = port;
        createConnection(5); // SUPPRESS CHECKSTYLE
    }

    public GaugeConnection(Socket socket) {
        gaugeSocket = socket;
        port = socket.getPort();
    }

    private static MessageLength getMessageLength(InputStream is) throws IOException {
        CodedInputStream codedInputStream = CodedInputStream.newInstance(is);
        long size = codedInputStream.readRawVarint64();
        return new MessageLength(size, codedInputStream);
    }

    private static byte[] toBytes(MessageLength messageLength) throws IOException {
        long messageSize = messageLength.getLength();
        CodedInputStream stream = messageLength.getRemainingStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int i = 0; i < messageSize; i++) {
            outputStream.write(stream.readRawByte());
        }

        return outputStream.toByteArray();
    }

    private void createConnection(int tries) {
        if (tries == 0) {
            throw new RuntimeException("Gauge API not started");
        }
        try {
            gaugeSocket = new Socket("localhost", port);
        } catch (IOException e) {
            try {
                //waits for the process to start accepting connection
                Thread.sleep(1000); // SUPPRESS CHECKSTYLE
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            createConnection(tries - 1);
        }
    }

    /**
     * Fetches all the steps in the gauge project as a list.
     *
     * @return a list of all steps
     * @throws IOException
     */
    public List<StepValue> fetchAllSteps() throws IOException {
        Api.APIMessage message = getStepRequest();
        Api.APIMessage response = getAPIResponse(message);
        Api.GetAllStepsResponse allStepsResponse = response.getAllStepsResponse();
        List<StepValue> steps = new ArrayList<StepValue>();
        for (Spec.ProtoStepValue stepValueResponse : allStepsResponse.getAllStepsList()) {
            StepValue stepValue = new StepValue(stepValueResponse.getStepValue(), stepValueResponse.getParameterizedStepValue(), stepValueResponse.getParametersList());
            steps.add(stepValue);
        }
        return steps;
    }

    /**
     * Fetches all the concepts in the Gauge project as a list of ConceptInfos which has details of file location.
     *
     * @return a list of all the ConceptInfo
     * @throws IOException
     */
    public List<ConceptInfo> fetchAllConcepts() throws IOException {
        Api.APIMessage message = getConceptRequest();
        Api.APIMessage response = getAPIResponse(message);
        Api.GetAllConceptsResponse allConceptsResponse = response.getAllConceptsResponse();
        List<ConceptInfo> conceptsInfo = new ArrayList<ConceptInfo>();
        for (Api.ConceptInfo conceptInfoResponse : allConceptsResponse.getConceptsList()) {
            Spec.ProtoStepValue protoStepValue = conceptInfoResponse.getStepValue();
            StepValue stepValue = new StepValue(protoStepValue.getStepValue(), protoStepValue.getParameterizedStepValue(), protoStepValue.getParametersList());
            ConceptInfo conceptInfo = new ConceptInfo(stepValue, conceptInfoResponse.getFilepath(), conceptInfoResponse.getLineNumber());
            conceptsInfo.add(conceptInfo);
        }
        return conceptsInfo;
    }

    /**
     * Gets the Absolute path to libs location for the particular language plugin.
     *
     * @param language - The language plugin name, eg. java
     * @return absolute path to libs location
     * @throws IOException
     * @throws com.thoughtworks.gauge.PluginNotInstalledException
     */
    public String getLibPath(String language) throws IOException, PluginNotInstalledException {
        Api.APIMessage message = getLibPathRequest(language);
        Api.APIMessage response = getAPIResponse(message);
        if (response.getMessageType().equals(Api.APIMessage.APIMessageType.ErrorResponse)) {
            throw new PluginNotInstalledException(response.getError().getError());
        }
        return response.getLibPathResponse().getPath();
    }

    private Api.APIMessage getAPIResponse(Api.APIMessage message) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CodedOutputStream cos = CodedOutputStream.newInstance(stream);
        byte[] bytes = message.toByteArray();
        cos.writeRawVarint64(bytes.length);
        cos.flush();
        stream.write(bytes);
        synchronized (gaugeSocket) {
            gaugeSocket.getOutputStream().write(stream.toByteArray());
            gaugeSocket.getOutputStream().flush();

            InputStream remoteStream = gaugeSocket.getInputStream();
            MessageLength messageLength = getMessageLength(remoteStream);
            bytes = toBytes(messageLength);
        }
        Api.APIMessage apiMessage = Api.APIMessage.parseFrom(bytes);
        return apiMessage;
    }

    /**
     * Gets the location of gauge installation on the system.
     *
     * @return gauge installation file
     * @throws IOException
     */
    public File getInstallationRoot() throws IOException {
        Api.APIMessage installationRootRequest = getInstallationRootRequest();
        Api.APIMessage response = getAPIResponse(installationRootRequest);
        Api.GetInstallationRootResponse allStepsResponse = response.getInstallationRootResponse();
        File installationRoot = new File(allStepsResponse.getInstallationRoot());
        if (installationRoot.exists()) {
            return installationRoot;
        } else {
            throw new IOException(installationRoot + " does not exist");
        }
    }

    public StepValue getStepValue(String stepText) {
        return getStepValue(stepText, false);
    }

    /**
     * Gets the step value for a particular step name.
     *
     * @param stepText       - The name of the step, eg. login as "admin"
     * @param hasInlineTable - set to true if the step has an inline table parameter
     * @return value of the given step name
     */
    public StepValue getStepValue(String stepText, boolean hasInlineTable) {
        Api.APIMessage stepValueRequest = getStepValueRequest(stepText, hasInlineTable);
        Api.APIMessage response;
        try {
            response = getAPIResponse(stepValueRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Api.GetStepValueResponse stepValueResponse = response.getStepValueResponse();
        Spec.ProtoStepValue protoStepValue = stepValueResponse.getStepValue();
        String stepValue = protoStepValue.getStepValue();
        List<String> parametersList = protoStepValue.getParametersList();
        String parameterizedStepValue = protoStepValue.getParameterizedStepValue();
        return new StepValue(stepValue, parameterizedStepValue, parametersList);
    }

    /**
     * Check if gauge connection is still active.
     *
     * @return true if connected
     */
    public boolean isConnected() {
        return gaugeSocket.isConnected();
    }

    /**
     * Closes the connection.
     *
     * @throws IOException - If fails to close the socket
     */
    public void close() throws IOException {
        gaugeSocket.close();
    }

    /**
     * Sends a request to gauge to perform a step name refactoring on the project.
     *
     * @param oldName old Step Name
     * @param newName New Step Name
     * @return RefactoringResponse message
     * @throws Exception On failure in connecting to gauge core or if invalid message is received.
     */
    public Api.PerformRefactoringResponse sendPerformRefactoringRequest(String oldName, String newName) throws Exception {
        Api.APIMessage refactoringRequest = createPerformRefactoringRequest(oldName, newName);
        Api.APIMessage response = getAPIResponse(refactoringRequest);
        return response.getPerformRefactoringResponse();
    }

    public Api.ExtractConceptResponse sendGetExtractConceptRequest(List<Api.step> steps, Api.step concept, boolean changeAcrossProject, String fileName, Api.textInfo selectedTextInfo) throws Exception {
        Api.APIMessage request = createExtractConceptRequest(steps, concept, changeAcrossProject, fileName, selectedTextInfo);
        Api.APIMessage response = getAPIResponse(request);
        return response.getExtractConceptResponse();
    }

    private Api.APIMessage getStepRequest() {
        Api.GetAllStepsRequest stepRequest = Api.GetAllStepsRequest.newBuilder().build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.GetAllStepsRequest)
                .setMessageId(GET_STEP_MSG_ID)
                .setAllStepsRequest(stepRequest)
                .build();
    }

    private Api.APIMessage getInstallationRootRequest() {
        Api.GetInstallationRootRequest installationRootRequest = Api.GetInstallationRootRequest.newBuilder().build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.GetInstallationRootRequest)
                .setMessageId(GET_INSTALLATION_ROOT_MSG_ID)
                .setInstallationRootRequest(installationRootRequest)
                .build();
    }

    private Api.APIMessage getStepValueRequest(String stepText, boolean hasInlineTable) {
        Api.GetStepValueRequest stepValueRequest = Api.GetStepValueRequest.newBuilder().setStepText(stepText).setHasInlineTable(hasInlineTable).build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.GetStepValueRequest)
                .setMessageId(GET_STEP_VALUE_MSG_ID)
                .setStepValueRequest(stepValueRequest)
                .build();
    }

    private Api.APIMessage getLibPathRequest(String language) {
        Api.GetLanguagePluginLibPathRequest libPathRequest = Api.GetLanguagePluginLibPathRequest.newBuilder().setLanguage(language).build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.GetLanguagePluginLibPathRequest)
                .setMessageId(GET_LIB_PATH_MSG_ID)
                .setLibPathRequest(libPathRequest)
                .build();
    }

    private Api.APIMessage getConceptRequest() {
        Api.GetAllConceptsRequest conceptRequest = Api.GetAllConceptsRequest.newBuilder().build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.GetAllConceptsRequest)
                .setMessageId(GET_CONCEPT_MSG_ID)
                .setAllConceptsRequest(conceptRequest)
                .build();
    }

    private Api.APIMessage createPerformRefactoringRequest(String oldName, String newName) {
        Api.PerformRefactoringRequest performRefactoringRequest = Api.PerformRefactoringRequest.newBuilder()
                .setOldStep(oldName)
                .setNewStep(newName)
                .build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.PerformRefactoringRequest)
                .setMessageId(PERFORM_REFACTORING_MSG_ID)
                .setPerformRefactoringRequest(performRefactoringRequest)
                .build();
    }

    private Api.APIMessage createExtractConceptRequest(List<Api.step> steps, Api.step concept, boolean changeAcrossProject, String fileName, Api.textInfo selectedTextInfo) {
        Api.ExtractConceptRequest request = Api.ExtractConceptRequest.newBuilder().addAllSteps(steps).setChangeAcrossProject(changeAcrossProject)
                .setSelectedTextInfo(selectedTextInfo).setConceptFileName(fileName).setConceptName(concept).build();
        return Api.APIMessage.newBuilder()
                .setMessageType(Api.APIMessage.APIMessageType.ExtractConceptRequest)
                .setMessageId(EXTRACT_CONCEPT_MSG_ID)
                .setExtractConceptRequest(request)
                .build();
    }
}
