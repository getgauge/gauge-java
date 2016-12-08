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

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Custom Table structure used as parameter in steps.
 */
public class Table {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String DASH = "-";
    private static final String PIPE = "|";
    private static final char SPACE_AS_CHAR = " ".charAt(0);
    private final List<String> headers;
    private final List<List<String>> rows;
    private final List<TableRow> tableRows;

    public Table(List<String> headers) {
        this.headers = headers;
        rows = new ArrayList<List<String>>();
        tableRows = new ArrayList<TableRow>();
    }

    public void addRow(List<String> row) {
        if (row.size() != headers.size()) {
            throw new RowSizeMismatchException(String.format("Row size mismatch. Expected row size: %d. Obtained row size: %d.", headers.size(), row.size()));
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
     * Gets a Column name by index
     * @param columnIndex
     * @return a single column name by given column index.
     */
    public String getColumnName(int columnIndex) {
        if (columnIndex < 0 || columnIndex > getColumnNames().size()) {
            throw new ColumnNotFoundException(String.format("Column with index %d not found. Actual column size: %d.", columnIndex, getColumnNames().size()));
        }
        return getColumnNames().get(columnIndex);
    }

    /**
     * @return List of Rows in the table. Each Row is represented by a TableRow.
     */
    public List<TableRow> getTableRows() {
        return tableRows;
    }

    /**
     * @return List of TableRows in the table. Each Row is represented by a List of String values
     * according to the order of column names
     * @deprecated Use getTableRows() method instead of this.
     */
    @Deprecated
    public List<List<String>> getRows() {
        return rows;
    }

    /**
     * Get all the values of a column in Table.
     *
     * @param columnName - The column name of the Table
     * @return List of values against a column in Table.
     */
    public List<String> getColumnValues(String columnName) {
        int columnIndex = headers.indexOf(columnName);
        return getColumnValues(columnIndex);
    }

    /**
     * Get all the values of a column in a Table.
     *
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
    public String toString() {
        int maxStringLength = getMaxStringLength();
        if (maxStringLength >= 0) {
            return formatAsMarkdownTable(maxStringLength);
        }
        return StringUtils.EMPTY;
    }

    private String formatAsMarkdownTable(
            int maxStringLength) {

        List<String> formattedHeaderAndRows = new ArrayList<String>();
        addHeader(maxStringLength, formattedHeaderAndRows);
        addDashes(maxStringLength, formattedHeaderAndRows);
        addValues(maxStringLength, formattedHeaderAndRows);
        return Joiner.on(LINE_SEPARATOR).join(formattedHeaderAndRows);
    }

    private void addDashes(
            int maxStringLength,
            List<String> formattedHeaderAndRows) {

        String dashesString = Joiner.on(StringUtils.EMPTY).join(Collections.nCopies(maxStringLength, DASH));
        List<String> dashes = Collections.nCopies(headers.size(), dashesString);
        String formattedDashes = formattedRow(dashes, maxStringLength);
        formattedHeaderAndRows.add(formattedDashes);
    }

    private void addHeader(
            int maxStringLength,
            List<String> formattedHeaderAndRows) {

        String formattedHeaders = formattedRow(headers, maxStringLength);
        formattedHeaderAndRows.add(formattedHeaders);
    }

    private void addValues(
            int maxStringLength,
            List<String> formattedHeaderAndRows) {

        for (TableRow tableRow : tableRows) {
            formattedHeaderAndRows.add(formattedRow(tableRow.getCellValues(), maxStringLength));
        }
    }

    private String formattedRow(List<String> strings, int maxStringLength) {
        List<String> formattedStrings = Lists.transform(strings, format(maxStringLength));
        return PIPE + Joiner.on(PIPE).join(formattedStrings) + PIPE;
    }

    private Function<String, String> format(
            final int maxStringLength) {


        return new Function<String, String>() {

            @Override
            public String apply(
                    String input) {


                return Strings.padEnd(input, maxStringLength, SPACE_AS_CHAR);
            }
        };
    }

    private Integer getMaxStringLength() {

        List<Integer> maxs = new ArrayList<Integer>();
        maxs.add(getMaxStringSize(headers));
        for (TableRow tableRow : tableRows) {
            maxs.add(getMaxStringSize(tableRow.getCellValues()));
        }
        return Collections.max(maxs);
    }

    private int getMaxStringSize(List<String> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            return -1;
        }
        return Collections.max(candidates, maxStringLength()).length();
    }

    private Comparator<String> maxStringLength() {

        return new Comparator<String>() {

            @Override
            public int compare(
                    String o1,
                    String o2) {
                if (o1.length() < o2.length()) {
                    return -1;
                }
                return 1;
            }
        };
    }
}
