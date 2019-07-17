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

import com.thoughtworks.gauge.Logger;

import java.net.Socket;

/**
 * Makes 2 important connections to the gauge core
 * <ul>
 * <li>Core connection where messages are responded to based on message type.
 * <li>API connection used to for asking gauge for common actions.
 * </ul>
 */
public class GaugeConnector {

    public static final String LOCALHOST = "127.0.0.1";
    private Socket gaugeSocket;

    public void makeConnectionsToGaugeCore(int gaugeInternalPort) {
        gaugeSocket = connect(gaugeInternalPort);
    }

    private static Socket connect(int port) {
        Socket clientSocket;
        while (true) {
            try {
                clientSocket = new Socket(LOCALHOST, port);
                break;
            } catch (Exception e) {
                Logger.error(String.format("Error occurred while trying to connect to %d port:\n%s\n%s", port), e);
            }
        }
        Logger.debug(String.format("Connected to %s:%d", LOCALHOST, port));
        return clientSocket;
    }

    public Socket getGaugeSocket() {
        return gaugeSocket;
    }
}
