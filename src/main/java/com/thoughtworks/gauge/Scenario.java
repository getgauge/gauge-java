package com.thoughtworks.gauge;

import java.util.List;

public class Scenario {
    private String name;
    private Boolean isFailing;
    private List<String> tags;

    public Scenario(String name, boolean isFailing, List<String> tags) {
        this.name = name;
        this.isFailing = isFailing;
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public Boolean getIsFailing() {
        return isFailing;
    }

    public String getName() {
        return name;
    }

}