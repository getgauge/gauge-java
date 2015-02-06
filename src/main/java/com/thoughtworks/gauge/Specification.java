// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

package com.thoughtworks.gauge;

import java.util.List;

public class Specification {
    private String name;
    private String fileName;
    private Boolean isFailing;
    private List<String> tags;

    public Specification(String name, String fileName, boolean isFailing, List<String> tags) {
        this.name = name;
        this.fileName = fileName;
        this.isFailing = isFailing;
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public Boolean getIsFailing() {
        return isFailing;
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }
}
