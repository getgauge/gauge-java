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
