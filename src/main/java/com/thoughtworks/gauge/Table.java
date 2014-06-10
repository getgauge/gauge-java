package com.thoughtworks.gauge;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<String> headers;
    private final List<List<String>> rows;

    public Table(List<String> headers) {
        this.headers = headers;
        rows = new ArrayList<List<String>>();
    }

    void addRow(List<String> row) {
        rows.add(row);
    }

    public List<String> getColumnNames() {
        return headers;
    }

    public List<List<String>> getRows() {
        return rows;
    }
}
