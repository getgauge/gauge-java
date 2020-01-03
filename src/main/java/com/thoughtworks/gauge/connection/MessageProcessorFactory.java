// Copyright 2019 ThoughtWorks, Inc.

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


import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.datastore.DataStoreInitializer;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.processor.CacheFileRequestProcessor;
import com.thoughtworks.gauge.processor.DefaultMessageProcessor;
import com.thoughtworks.gauge.processor.ExecuteStepProcessor;
import com.thoughtworks.gauge.processor.IMessageProcessor;
import com.thoughtworks.gauge.processor.KillProcessProcessor;
import com.thoughtworks.gauge.processor.RefactorRequestProcessor;
import com.thoughtworks.gauge.processor.ScenarioExecutionEndingProcessor;
import com.thoughtworks.gauge.processor.ScenarioExecutionStartingProcessor;
import com.thoughtworks.gauge.processor.SpecExecutionEndingProcessor;
import com.thoughtworks.gauge.processor.SpecExecutionStartingProcessor;
import com.thoughtworks.gauge.processor.StepExecutionEndingProcessor;
import com.thoughtworks.gauge.processor.StepExecutionStartingProcessor;
import com.thoughtworks.gauge.processor.StepNameRequestProcessor;
import com.thoughtworks.gauge.processor.StepNamesRequestProcessor;
import com.thoughtworks.gauge.processor.StepPositionsRequestProcessor;
import com.thoughtworks.gauge.processor.SuiteExecutionEndingProcessor;
import com.thoughtworks.gauge.processor.SuiteExecutionStartingProcessor;
import com.thoughtworks.gauge.processor.ValidateStepProcessor;
import com.thoughtworks.gauge.registry.ClassInitializerRegistry;
import com.thoughtworks.gauge.registry.StepRegistry;
import com.thoughtworks.gauge.scan.ClasspathScanner;
import com.thoughtworks.gauge.scan.CustomClassInitializerScanner;
import com.thoughtworks.gauge.scan.HooksScanner;
import com.thoughtworks.gauge.scan.StaticScanner;
import com.thoughtworks.gauge.scan.StepsScanner;
import com.thoughtworks.gauge.screenshot.CustomScreenshotScanner;
import gauge.messages.Messages;

import java.util.HashMap;

/**
 * Receives messages from gauge core and processes them using the relevant MessageProcessor and returns a
 * valid response.
 */
public class MessageProcessorFactory {

    private HashMap<Messages.Message.MessageType, IMessageProcessor> messageProcessors;
    private StepRegistry stepRegistry;
    private StaticScanner staticScanner;

    public MessageProcessorFactory(StaticScanner staticScanner) {
        this.staticScanner = staticScanner;
        stepRegistry = staticScanner.getRegistry();
        messageProcessors = initializeMessageProcessor();
    }

    public StepRegistry getStepRegistry() {
        return this.stepRegistry;
    }

    public IMessageProcessor getProcessor(Messages.Message.MessageType request) {
        if (request == Messages.Message.MessageType.SuiteDataStoreInit) {
            ClasspathScanner classpathScanner = new ClasspathScanner();
            classpathScanner.scan(new StepsScanner(staticScanner.getRegistry()), new HooksScanner(), new CustomScreenshotScanner(), new CustomClassInitializerScanner());
            this.initializeExecutionMessageProcessors();
        }
        if (messageProcessors.containsKey(request)) {
            return messageProcessors.get(request);
        }
        return new DefaultMessageProcessor();
    }

    private HashMap<Messages.Message.MessageType, IMessageProcessor> initializeMessageProcessor() {
        return new HashMap<Messages.Message.MessageType, IMessageProcessor>() {
            {
                put(Messages.Message.MessageType.StepNameRequest, new StepNameRequestProcessor(stepRegistry));
                put(Messages.Message.MessageType.StepNamesRequest, new StepNamesRequestProcessor(stepRegistry));
                put(Messages.Message.MessageType.RefactorRequest, new RefactorRequestProcessor(stepRegistry));
                put(Messages.Message.MessageType.CacheFileRequest, new CacheFileRequestProcessor(staticScanner));
                put(Messages.Message.MessageType.StepPositionsRequest, new StepPositionsRequestProcessor(stepRegistry));
                put(Messages.Message.MessageType.StepValidateRequest, new ValidateStepProcessor(stepRegistry));
                put(Messages.Message.MessageType.StubImplementationCodeRequest, new StubImplementationCodeProcessor());
                put(Messages.Message.MessageType.KillProcessRequest, new KillProcessProcessor());
            }
        };
    }

    private void initializeExecutionMessageProcessors() {
        ParameterParsingChain chain = new ParameterParsingChain();
        ClassInstanceManager instanceManager = new ClassInstanceManager(ClassInitializerRegistry.classInitializer());
        Gauge.setInstanceManager(instanceManager);
        messageProcessors.put(Messages.Message.MessageType.ExecutionStarting, new SuiteExecutionStartingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.ExecutionEnding, new SuiteExecutionEndingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.SpecExecutionStarting, new SpecExecutionStartingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.SpecExecutionEnding, new SpecExecutionEndingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.ScenarioExecutionStarting, new ScenarioExecutionStartingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.ScenarioExecutionEnding, new ScenarioExecutionEndingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.StepExecutionStarting, new StepExecutionStartingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.StepExecutionEnding, new StepExecutionEndingProcessor(instanceManager));
        messageProcessors.put(Messages.Message.MessageType.ExecuteStep, new ExecuteStepProcessor(instanceManager, chain, staticScanner.getRegistry()));
        messageProcessors.put(Messages.Message.MessageType.SuiteDataStoreInit, new DataStoreInitializer());
        messageProcessors.put(Messages.Message.MessageType.SpecDataStoreInit, new DataStoreInitializer());
        messageProcessors.put(Messages.Message.MessageType.ScenarioDataStoreInit, new DataStoreInitializer());
    }
}
