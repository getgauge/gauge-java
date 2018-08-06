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

/**
 * This class represents a single table cell object containing
 * the cell's column name and its associated value.
 */
public class TableCell {
    private String columnName;
    private String value;

    public TableCell(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TableCell{"
                + "columnName=" + columnName
                + ", "
                + "value=" + value
                + '}';
    }
}
