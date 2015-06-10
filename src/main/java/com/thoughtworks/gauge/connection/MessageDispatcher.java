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
import com.thoughtworks.gauge.datastore.DataStoreInitializer;
import com.thoughtworks.gauge.processor.*;
import gauge.messages.Messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Receives messages from gauge core and processes them using the relevant MessageProcessor and returns a
 * valid response.
 */
public class MessageDispatcher {

    private final HashMap<Messages.Message.MessageType, IMessageProcessor> messageProcessors;

    public MessageDispatcher() {
        messageProcessors = new HashMap<Messages.Message.MessageType, IMessageProcessor>() {{
            put(Messages.Message.MessageType.ExecutionStarting, new SuiteExecutionStartingProcessor());
            put(Messages.Message.MessageType.ExecutionEnding, new SuiteExecutionEndingProcessor());
            put(Messages.Message.MessageType.SpecExecutionStarting, new SpecExecutionStartingProcessor());
            put(Messages.Message.MessageType.SpecExecutionEnding, new SpecExecutionEndingProcessor());
            put(Messages.Message.MessageType.ScenarioExecutionStarting, new ScenarioExecutionStartingProcessor());
            put(Messages.Message.MessageType.ScenarioExecutionEnding, new ScenarioExecutionEndingProcessor());
            put(Messages.Message.MessageType.StepExecutionStarting, new StepExecutionStartingProcessor());
            put(Messages.Message.MessageType.StepExecutionEnding, new StepExecutionEndingProcessor());
            put(Messages.Message.MessageType.ExecuteStep, new ExecuteStepProcessor());
            put(Messages.Message.MessageType.StepValidateRequest, new ValidateStepProcessor());
            put(Messages.Message.MessageType.StepNamesRequest, new StepNamesRequestProcessor());
            put(Messages.Message.MessageType.SuiteDataStoreInit, new DataStoreInitializer());
            put(Messages.Message.MessageType.SpecDataStoreInit, new DataStoreInitializer());
            put(Messages.Message.MessageType.ScenarioDataStoreInit, new DataStoreInitializer());
            put(Messages.Message.MessageType.KillProcessRequest, new KillProcessProcessor());
            put(Messages.Message.MessageType.StepNameRequest, new StepNameRequestProcessor());
            put(Messages.Message.MessageType.RefactorRequest, new RefactorRequestProcessor());
        }};
    }

    public void dispatchMessages(GaugeConnector connector) throws IOException {
        Socket gaugeSocket = connector.getGaugeSocket();
        InputStream inputStream = gaugeSocket.getInputStream();
        while (isConnected(gaugeSocket)) {
            try {
                MessageLength messageLength = getMessageLength(inputStream);
                byte[] bytes = toBytes(messageLength);
                Messages.Message message = Messages.Message.parseFrom(bytes);
                if (!messageProcessors.containsKey(message.getMessageType())) {
                    System.err.println("Invalid message type received " + message.getMessageType());
                } else {
                    IMessageProcessor messageProcessor = messageProcessors.get(message.getMessageType());
                    Messages.Message response = messageProcessor.process(message);
                    writeMessage(gaugeSocket, response);
                    if (message.getMessageType() == Messages.Message.MessageType.ExecutionEnding
                            || message.getMessageType() == Messages.Message.MessageType.KillProcessRequest) {
                        gaugeSocket.close();
                        break;
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                System.err.println(throwable.toString());
                return;
            }
        }

    }


    private MessageLength getMessageLength(InputStream is) throws IOException {
        CodedInputStream codedInputStream = CodedInputStream.newInstance(is);
        long size = codedInputStream.readRawVarint64();
        return new MessageLength(size, codedInputStream);
    }

    private byte[] toBytes(MessageLength messageLength) throws IOException {
        long messageSize = messageLength.length;
        CodedInputStream stream = messageLength.remainingStream;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int i = 0; i < messageSize; i++) {
            outputStream.write(stream.readRawByte());
        }

        return outputStream.toByteArray();
    }

    private void writeMessage(Socket socket, Messages.Message message) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CodedOutputStream cos = CodedOutputStream.newInstance(stream);
        byte[] bytes = message.toByteArray();
        cos.writeRawVarint64(bytes.length);
        cos.flush();
        stream.write(bytes);
        socket.getOutputStream().write(stream.toByteArray());
        socket.getOutputStream().flush();
    }

    private boolean isConnected(Socket socket) {
        return !socket.isClosed() && socket.isConnected();
    }
}
