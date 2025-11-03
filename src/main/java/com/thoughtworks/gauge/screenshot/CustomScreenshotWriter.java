/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.screenshot;

/**
 * Implement this interface to take Custom Screenshots on Failure. It overrides the default screenshot taking mechanism.
 * The captured screenshots can be seen on the reports on failure.
 * If multiple implementations are found, one will be picked randomly to capture screenshots.
 * Implementation of this interface should capture screenshot and write them into a unique file
 * inside screenshots directory. Use "gauge_screenshots_dir" env to get screenshot directory path"
 */
public interface CustomScreenshotWriter {

    /**
     * @return Name of the screenshot file taken.
     */
    String takeScreenshot();

}
