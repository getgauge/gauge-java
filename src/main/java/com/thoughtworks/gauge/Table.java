/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom Table structure used as parameter in steps.
 */
public class Table {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DASH = "-";
    private static final String PIPE = "|";
    private static final char SPACE_AS_CHAR = ' ';
    private final List<String> headers;
    private final List<List<String>> rows;
    private final List<TableRow> tableRows;

    public Table(List<String> headers) {
        this.headers = headers;
        rows = new ArrayList<>();
        tableRows = new ArrayList<>();
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
     * Gets a Column name by index.
     *
     * @param columnIndex
     * @return a single column name by given column index.
     */
    public String getColumnName(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnNames().size()) {
            throw new IndexOutOfBoundsException(String.format("Column with index %d not found. Actual column size: %d.", columnIndex, getColumnNames().size()));
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
     * @return List of TableRows in the table. Each Row is represented by a List
     *         of String values according to the order of column names
     * @deprecated Use getTableRows() method instead of this.
     */
    @Deprecated
    public List<List<String>> getRows() {
        return rows;
    }

    /**
     * Get all the values of a column in Table.
     *
     * @param columnName
     *            - The column name of the Table
     * @return List of values against a column in Table.
     */
    public List<String> getColumnValues(String columnName) {
        int columnIndex = headers.indexOf(columnName);
        return getColumnValues(columnIndex);
    }

    /**
     * Get all the values of a column in a Table.
     *
     * @param columnIndex
     *            - The column index of the table
     * @return List of row values of a given column index in a Table.
     */
    public List<String> getColumnValues(int columnIndex) {
        if (columnIndex >= 0) {
            return rows.stream().map(row -> row.get(columnIndex)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        int maxStringLength = getMaxStringLength();
        if (maxStringLength >= 0) {
            return formatAsMarkdownTable(maxStringLength);
        }
        return "";
    }

    private String formatAsMarkdownTable(int maxStringLength) {
        List<String> formattedHeaderAndRows = new ArrayList<>();
        addHeader(maxStringLength, formattedHeaderAndRows);
        addDashes(maxStringLength, formattedHeaderAndRows);
        addValues(maxStringLength, formattedHeaderAndRows);
        return Joiner.on(LINE_SEPARATOR).join(formattedHeaderAndRows);
    }

    private void addDashes(int maxStringLength, List<String> formattedHeaderAndRows) {
        String dashesString = Joiner.on("").join(Collections.nCopies(maxStringLength, DASH));
        List<String> dashes = Collections.nCopies(headers.size(), dashesString);
        String formattedDashes = formattedRow(dashes, maxStringLength);
        formattedHeaderAndRows.add(formattedDashes);
    }

    private void addHeader(int maxStringLength, List<String> formattedHeaderAndRows) {
        String formattedHeaders = formattedRow(headers, maxStringLength);
        formattedHeaderAndRows.add(formattedHeaders);
    }

    private void addValues(int maxStringLength, List<String> formattedHeaderAndRows) {
        tableRows.stream().map(tableRow -> formattedRow(tableRow.getCellValues(), maxStringLength)).forEach(formattedHeaderAndRows::add);
    }

    private String formattedRow(List<String> strings, int maxStringLength) {
        List<String> formattedStrings = Lists.transform(strings, format(maxStringLength));
        return PIPE + Joiner.on(PIPE).join(formattedStrings) + PIPE;
    }

    private Function<String, String> format(final int maxStringLength) {
        return input -> Strings.padEnd(input, maxStringLength, SPACE_AS_CHAR);
    }

    private Integer getMaxStringLength() {
        List<Integer> maxs = new ArrayList<>();
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
        return Comparator.comparingInt(String::length);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (headers == null ? 0 : headers.hashCode());
        result = prime * result + (rows == null ? 0 : rows.hashCode());
        result = prime * result + (tableRows == null ? 0 : tableRows.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Table other = (Table) obj;
        return headers.equals(other.getColumnNames()) && tableRows.equals(other.getTableRows());
    }
}
