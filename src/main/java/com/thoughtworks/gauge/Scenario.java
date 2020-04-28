/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the information about the current Scenario executing at Runtime.
 */
public class Scenario {
    private String name = "";
    private Boolean isFailing = false;
    private List<String> tags = new ArrayList<String>();

    public Scenario(String name, boolean isFailing, List<String> tags) {
        this.name = name;
        this.isFailing = isFailing;
        this.tags = tags;
    }

    public Scenario() {
    }

    /**
     * @return List of all tags in just the scenario
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @return True if the scenario or spec is failing
     */
    public Boolean getIsFailing() {
        return isFailing;
    }

    /**
     * @return Name of the Scenario as mentioned in the scenario heading
     */
    public String getName() {
        return name;
    }

}
