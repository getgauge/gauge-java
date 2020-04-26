/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

/**
 * Interface to provide a Custom Class Initializer for managing Class Objects.
 */
public interface ClassInitializer {
    Object initialize(Class<?> classToInitialize) throws Exception;
}
