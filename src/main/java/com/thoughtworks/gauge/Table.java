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
 * Custom Table structure used as parameter in steps
 */
public class Table {
    private final List<String> headers;
    private final List<List<String>> rows;
    private final List<TableRow> tableRows;

    public Table(List<String> headers) {
        this.headers = headers;
        rows = new ArrayList<List<String>>();
        tableRows = new ArrayList<TableRow>();
    }

    public void addRow(List<String> row) {
        if (row.size() != headers.size()){
            throw new RowSizeMismatchException(String.format("Row size mismatch. Expected row size: %d. Obtained row size: %d.",headers.size(), row.size()));
        }
        rows.add(row);
        TableRow rowToAdd = new TableRow();
        for (String header : headers) {
            rowToAdd.addCell(header, row.get(headers.indexOf(header)));
        }
        tableRows.add(rowToAdd);
    }

    /**
     * @return List of Names of the Columns on the table
     */
    public List<String> getColumnNames() {
        return headers;
    }

    /**
     * @return List of Rows in the table. Each Row is represented by a TableRow.
     */
    public List<TableRow> getTableRows(){
        return tableRows;
    }
    
    /**
     * @deprecated Use getTableRows() method instead of this.
     * @return List of TableRows in the table. Each Row is represented by a List of String values
     * according to the order of column names
     */
    @Deprecated
    public List<List<String>> getRows() {
        return rows;
    }

    /**
     * Get all the values of a column in Table.
     * @param columnName - The column name of the Table
     * @return List of values against a column in Table.
     */
    public List<String> getColumnValues(String columnName) {
        int columnIndex = headers.indexOf(columnName);
        return getColumnValues(columnIndex);
    }

    /**
     * Get all the values of a column in a Table.
     * @param columnIndex - The column index of the table
     * @return List of row values of a given column index in a Table.
     */
    public List<String> getColumnValues(int columnIndex) {
        List<String> columnValues = new ArrayList<String>();
        if (columnIndex >= 0) {
            for (List<String> row : rows) {
                columnValues.add(row.get(columnIndex));
            }
        }
        return columnValues;
    }
    
    @Override
    public String toString(){
        return "Table{" +
                        tableRows.toString() +
                        '}';
    }
}
