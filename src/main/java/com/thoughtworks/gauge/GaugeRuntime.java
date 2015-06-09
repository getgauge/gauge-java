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

package com.thoughtworks.gauge;

import com.thoughtworks.gauge.connection.GaugeConnector;
import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.scan.ClasspathScanner;
import com.thoughtworks.gauge.screenshot.CustomScreenshotScanner;
import com.thoughtworks.gauge.scan.HooksScanner;
import com.thoughtworks.gauge.scan.StepsScanner;

/**
 * Holds Main for starting Gauge-java
 * 1. Makes connections to gauge
 * 2. Scans Classpath
 * 3. Dispatched all message responses
 */
public class GaugeRuntime {

    public static void main(String[] args) throws Exception {
        GaugeConnector connector = new GaugeConnector();
        connector.makeConnectionsToGaugeCore();
        new ClasspathScanner().scan(new StepsScanner(connector), new HooksScanner(), new CustomScreenshotScanner());
        new MessageDispatcher().dispatchMessages(connector);
    }

}
