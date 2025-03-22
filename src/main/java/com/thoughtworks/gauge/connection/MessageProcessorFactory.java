/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.connection;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.datastore.DataStoreInitializer;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.processor.*;
import com.thoughtworks.gauge.registry.ClassInitializerRegistry;
import com.thoughtworks.gauge.registry.StepRegistry;
import com.thoughtworks.gauge.scan.*;
import com.thoughtworks.gauge.screenshot.CustomScreenshotScanner;
import gauge.messages.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Receives messages from gauge core and processes them using the relevant MessageProcessor and returns a
 * valid response.
 */
public class MessageProcessorFactory {

    private static final Logger LOGGER = LogManager.getLogger(MessageProcessorFactory.class);

    private final ThreadLocal<HashMap<Messages.Message.MessageType, IMessageProcessor>> messageProcessors;
    private final StepRegistry stepRegistry;
    private final StaticScanner staticScanner;
    private final CountDownLatch scanLatch;
    private static final int SCAN_POLL_INTERVAL = 500;

    public MessageProcessorFactory(StaticScanner staticScanner) {
        this.staticScanner = staticScanner;
        stepRegistry = staticScanner.getRegistry();
        scanLatch = new CountDownLatch(1);
        Executors.newSingleThreadExecutor().submit(() -> {
            LOGGER.debug("Using reflection to scan dependencies for gauge implementations...");
            if (String.valueOf(System.getenv(GaugeConstant.PACKAGE_TO_SCAN)).isEmpty()) {
                LOGGER.warn("'" + GaugeConstant.PACKAGE_TO_SCAN + "' is not set. "
                        + "This may impact the start up time of gauge-java, and possibly cause a timeout error. "
                        + "Consider setting '" + GaugeConstant.PACKAGE_TO_SCAN + "' property to the packages that contain Gauge implementations.");
            }
            ClasspathScanner classpathScanner = new ClasspathScanner();
            classpathScanner.scan(new StepsScanner(stepRegistry), new HooksScanner(), new CustomScreenshotScanner(), new CustomClassInitializerScanner());
            LOGGER.debug("Scanned steps (using static parsing + reflections): ");
            for (String stepText : stepRegistry.keys()) {
                LOGGER.debug("\t{} : {}", stepText, stepRegistry.get(stepText).getName());
            }
            scanLatch.countDown();
        });
        messageProcessors = initializeMessageProcessor();
    }

    public IMessageProcessor getProcessor(Messages.Message.MessageType request) {
        try {
            scanLatch.await();
        } catch (InterruptedException e) {
            GaugeExceptionLogger.error(LOGGER, "Reflection scan could not be completed in a separate thread.", e);
        }
        if (!messageProcessors.get().containsKey(Messages.Message.MessageType.SuiteDataStoreInit)) {
            initializeExecutionMessageProcessors();
        }
        if (messageProcessors.get().containsKey(request)) {
            return messageProcessors.get().get(request);
        }
        LOGGER.warn("MessageProcessor not found for: {}", request);
        return new DefaultMessageProcessor();
    }

    private ThreadLocal<HashMap<Messages.Message.MessageType, IMessageProcessor>> initializeMessageProcessor() {
        return ThreadLocal.withInitial(() -> new HashMap<>() {
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
