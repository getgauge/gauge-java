/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with this hook, will be executed after every step in that particular class.
 * <p>
 * If there is more than one method annotated with @AfterClassSteps the order of execution is as follows:
 * <ul>
 * <li>Hooks which are not filtered by tags.
 * <li>Hooks filtered by tags.
 * </ul>
 * If there is more than one hook of these categories, they are executed in reverse alphabetical order based on method names.
 * </p>
 */
@Target(ElementType.METHOD)
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterClassSteps {

    /**
     * @return Array of tags to filter which steps the hook executes after based on the tags in the current scenario and spec.
     */
    String[] tags() default {};

    /**
     * @return OR: if hook should execute for the current execution context (spec and scenario) containing any of the tags provided
     * AND: if hook should execute for the current execution context (spec and scenario) containing all of the tags provided
     * Default is AND
     */
    Operator tagAggregation() default Operator.AND;
}
