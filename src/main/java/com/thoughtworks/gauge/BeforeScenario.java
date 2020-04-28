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
 * Methods annotated with this hook, execute before every scenario execution completes.
 * The scenario can be filtered by passing additional attributes.
 * <p>
 * If there is more than one method annotated with @BeforeScenario, the order of execution is as follows:
 * <ul>
 * <li>Hooks which are not filtered by tags.
 * <li>Hooks filtered by tags.
 * </ul>
 * If there is more than one hook of these categories, they are executed in alphabetical order based on method names.
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeScenario {

    /**
     * @return Array of tags to filter which scenarios the hook runs before.
     */
    String[] tags() default {};

    /**
     * @return OR: if hook should run for a scenario containing any of the tags provided
     * AND: if hook should run for a scenario containing all of the tags provided
     * Default is AND
     */
    Operator tagAggregation() default Operator.AND;
}
