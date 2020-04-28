/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the information about the current Specification executing at runtime.
 */
public class Specification {
    private String name = "";
    private String fileName = "";
    private Boolean isFailing = false;
    private List<String> tags = new ArrayList<>();

    public Specification(String name, String fileName, boolean isFailing, List<String> tags) {
        this.name = name;
        this.fileName = fileName;
        this.isFailing = isFailing;
        this.tags = tags;
    }

    public Specification() {
    }

    /**
     * @return List of all the tags in the Spec
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @return True if the current spec is failing.
     */
    public Boolean getIsFailing() {
        return isFailing;
    }

    /**
     * @return Full path to the Spec
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return The name of the Specification as mentioned in the Spec heading
     */
    public String getName() {
        return name;
    }
}
