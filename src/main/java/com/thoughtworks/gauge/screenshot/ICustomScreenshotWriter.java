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

package com.thoughtworks.gauge.screenshot;

/**
 * Implement this interface to take Custom Screenshots on Failure. It overrides the default screenshot taking mechanism.
 * The captured screenshots can be seen on the reports on failure.
 * If multiple implementations are found, one will be picked randomly to capture screenshots.
 * Implementation of this interface should capture screenshot and write them into a unique file
 * inside screenshots directory. Use "screenshots_dir" env to get screenshot directory path"
 */
public interface ICustomScreenshotWriter extends ICustomScreenshot {

    /**
     * @return Name of the screenshot file taken.
     */
    String takeScreenshot();

}
