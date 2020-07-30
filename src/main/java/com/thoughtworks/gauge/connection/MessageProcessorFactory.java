/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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

    private ThreadLocal<HashMap<Messages.Message.MessageType, IMessageProcessor>> messageProcessors;
    private StepRegistry stepRegistry;
    private StaticScanner staticScanner;
    private ClasspathScanner classpathScanner;

    public MessageProcessorFactory(StaticScanner staticScanner) {
        classpathScanner = new ClasspathScanner();
        this.staticScanner = staticScanner;
        stepRegistry = staticScanner.getRegistry();
        messageProcessors = initializeMessageProcessor();
    }

    public IMessageProcessor getProcessor(Messages.Message.MessageType request) {
        classpathScanner.scanOncePerThread(new StepsScanner(staticScanner.getRegistry()), new HooksScanner(), new CustomScreenshotScanner(), new CustomClassInitializerScanner());
        if (request == Messages.Message.MessageType.SuiteDataStoreInit) {
            this.initializeExecutionMessageProcessors();
        }
        if (messageProcessors.get().containsKey(request)) {
            return messageProcessors.get().get(request);
        }
        return new DefaultMessageProcessor();
    }

    private ThreadLocal<HashMap<Messages.Message.MessageType, IMessageProcessor>> initializeMessageProcessor() {
        return ThreadLocal.withInitial(() -> new HashMap<Messages.Message.MessageType, IMessageProcessor>() {
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
        });
    }

    private void initializeExecutionMessageProcessors() {
        ParameterParsingChain chain = new ParameterParsingChain();
        ThreadLocal<ClassInstanceManager> instanceManager = ThreadLocal.withInitial(() -> new ClassInstanceManager(ClassInitializerRegistry.classInitializer()));
        Gauge.setInstanceManager(instanceManager.get());
        HashMap<Messages.Message.MessageType, IMessageProcessor> mps = messageProcessors.get();
        mps.put(Messages.Message.MessageType.ExecutionStarting, new SuiteExecutionStartingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.ExecutionEnding, new SuiteExecutionEndingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.SpecExecutionStarting, new SpecExecutionStartingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.SpecExecutionEnding, new SpecExecutionEndingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.ScenarioExecutionStarting, new ScenarioExecutionStartingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.ScenarioExecutionEnding, new ScenarioExecutionEndingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.StepExecutionStarting, new StepExecutionStartingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.StepExecutionEnding, new StepExecutionEndingProcessor(instanceManager.get()));
        mps.put(Messages.Message.MessageType.ExecuteStep, new ExecuteStepProcessor(instanceManager.get(), chain, stepRegistry));
        mps.put(Messages.Message.MessageType.SuiteDataStoreInit, new DataStoreInitializer());
        mps.put(Messages.Message.MessageType.SpecDataStoreInit, new DataStoreInitializer());
        mps.put(Messages.Message.MessageType.ScenarioDataStoreInit, new DataStoreInitializer());
    }
}
