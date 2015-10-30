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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with this hook, execute after every Step execution completes.
 * The steps for which the hook executes can be filtered by passing additional attributes.
 * <p>
 * If there is more than one method annotated with @AfterStep, the order of execution is as follows:
 * <ul>
 * <li>Hooks which are not filtered by tags.
 * <li>Hooks filtered by tags.
 * </ul>
 * If there is more than one hook of these categories, they are executed in reverse alphabetical order based on method names.
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterStep {

    /**
     * @return Array of tags to filter which steps the hook runs after.
     */
    String[] tags() default {};

    /**
     * @return OR: if hook should execute for the current execution context (spec and scenario) containing any of the tags provided
     * AND: if hook should execute for the current execution context (spec and scenario) containing all of the tags provided
     * Default is AND
     */
    Operator tagAggregation() default Operator.AND;
}
