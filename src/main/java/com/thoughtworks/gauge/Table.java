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
 * Custom Table structure used as parameter is steps
 */
public class Table {
    private final List<String> headers;
    private final List<List<String>> rows;

    public Table(List<String> headers) {
        this.headers = headers;
        rows = new ArrayList<List<String>>();
    }

    public void addRow(List<String> row) {
        rows.add(row);
    }

    /**
     * @return - List of Names of the Columns on the table
     */
    public List<String> getColumnNames() {
        return headers;
    }

    /**
     * @return - List of Rows in the table. Each Row is represented by a List of String values
     * according to the order of column names
     */
    public List<List<String>> getRows() {
        return rows;
    }
}
