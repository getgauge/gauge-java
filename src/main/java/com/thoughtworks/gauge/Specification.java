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

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the information about the current Specification executing at Runtime
 */
public class Specification {
    private String name = "";
    private String fileName = "";
    private Boolean isFailing = false;
    private List<String> tags = new ArrayList<String>();

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
