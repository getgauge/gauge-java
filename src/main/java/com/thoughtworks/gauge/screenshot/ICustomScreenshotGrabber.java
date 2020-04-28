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
 */
@Deprecated
public interface ICustomScreenshotGrabber extends CustomScreenshot {


    /**
     * @return Byte array of the screenshot taken.
     * Return an empty Byte array if unable to capture screen.
     */
    byte[] takeScreenshot();

}
