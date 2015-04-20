// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

package com.thoughtworks.gauge.processor;


import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.refactor.JavaRefactoring;
import com.thoughtworks.gauge.refactor.RefactoringResult;
import gauge.messages.Messages;

public class RefactorRequestProcessor implements IMessageProcessor {

    public Messages.Message process(Messages.Message message) {
        Messages.RefactorRequest refactorRequest = message.getRefactorRequest();
        RefactoringResult result = new JavaRefactoring(StepValue.from(refactorRequest.getOldStepValue()),
                                                       StepValue.from(refactorRequest.getNewStepValue()),
                                                       refactorRequest.getParamPositionsList()).performRefactoring();

        return createRefactorResponse(message, result);
    }

    private Messages.Message createRefactorResponse(Messages.Message message, RefactoringResult result) {
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.RefactorResponse)
                .setRefactorResponse(Messages.RefactorResponse.newBuilder().setSuccess(result.passed()).setError(result.errorMessage()).addFilesChanged(result.fileChanged()).build())
                .build();
    }

}
