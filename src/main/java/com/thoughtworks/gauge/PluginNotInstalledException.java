package com.thoughtworks.gauge;

public class PluginNotInstalledException extends Exception {
    public PluginNotInstalledException(String error) {
        super(error);
    }
}
