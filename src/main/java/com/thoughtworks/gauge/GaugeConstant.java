/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

public class GaugeConstant {
    public static final String SCREENSHOT_ON_FAILURE_ENABLED = "screenshot_on_failure";
    public static final String PACKAGE_TO_SCAN = "package_to_scan";
    public static final String GAUGE_PROJECT_ROOT = "GAUGE_PROJECT_ROOT";
    public static final String GAUGE_CUSTOM_COMPILE_DIR = "gauge_custom_compile_dir";
    public static final String DEFAULT_SRC_DIR = "src/test/java";
    public static final String[] DEFAULT_SRC_DIRS = {"src/main/java", DEFAULT_SRC_DIR};
    public static final String SCREENSHOTS_DIR_ENV = "gauge_screenshots_dir";
    public static final String STREAMS_COUNT_ENV = "GAUGE_PARALLEL_STREAMS_COUNT";
    public static final String ENABLE_MULTITHREADING_ENV = "enable_multithreading";
    public static final String SCAN_EXTERNAL_LIBS = "scan_external_dependencies";
    public static final String LOCALHOST = "127.0.0.1";
}
