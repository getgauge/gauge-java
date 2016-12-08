package com.thoughtworks.gauge;

public class ColumnNotFoundException extends RuntimeException {
    public ColumnNotFoundException(final String error) {
        super(error);
    }
}
