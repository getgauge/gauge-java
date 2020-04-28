/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;


import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.refactor.JavaRefactoring;
import com.thoughtworks.gauge.refactor.RefactoringResult;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;

import java.util.List;

public class RefactorRequestProcessor implements IMessageProcessor {
    private final StepRegistry registry;

    public RefactorRequestProcessor(StepRegistry registry) {
        this.registry = registry;
    }

    public Messages.Message process(Messages.Message message) {
        Messages.RefactorRequest refactorRequest = message.getRefactorRequest();
        boolean saveChanges = refactorRequest.getSaveChanges();
        StepValue oldStepValue = StepValue.from(refactorRequest.getOldStepValue());
        StepValue newStepValue = StepValue.from(refactorRequest.getNewStepValue());
        String parameterizedStepValue = refactorRequest.getNewStepValue().getParameterizedStepValue();
        List<Messages.ParameterPosition> paramPositions = refactorRequest.getParamPositionsList();
        RefactoringResult result = new JavaRefactoring(oldStepValue, newStepValue, paramPositions, registry, parameterizedStepValue, saveChanges).performRefactoring();
        return createRefactorResponse(message, result);
    }

    private Messages.Message createRefactorResponse(Messages.Message message, RefactoringResult result) {
        Messages.RefactorResponse response = Messages.RefactorResponse.newBuilder()
                .setSuccess(result.passed())
                .setError(result.errorMessage())
                .addFilesChanged(result.fileChanged())
                .addFileChanges(result.fileChanges())
                .build();

        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.RefactorResponse)
                .setRefactorResponse(response)
                .build();
    }

}
