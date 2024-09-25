/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.util.*;

/**
 * Represents a Row of Data in a Table.
 */
public class TableRow {
    private final Map<String, String> cells = new LinkedHashMap<>();

    /**
     * Get the value of cell corresponding to a column name.
     *
     * @param columnName
     *            - The column name of TableRow.
     * @return The value of cell corresponding to a column name.
     */
    public String getCell(String columnName) {
        if (!cells.containsKey(columnName)) {
            return "";
        }
        return cells.get(columnName);
    }

    /**
     * Add a cell to the TableRow.
     *
     * @param columnName
     *            The column name against which the cell is added.
     * @param value
     *            The value to be stored in the cell.
     */
    public void addCell(String columnName, String value) {
        cells.put(columnName, value);
    }

    /**
     * Get the number of cells in TableRow.
     *
     * @return The number of cells in TableRow.
     */
    public int size() {
        return cells.size();
    }

    @Override
    public String toString() {
        return "TableRow{" + "cells=" + cells + '}';
    }

    /**
     * Returns a list containing the values of each cell in the table row.
     *
     * @return a list of the values of each cell in the table row.
     */
    public List<String> getCellValues() {
        // Since we have a LinkedHashMap, the order of values() is guaranteed.
        return new ArrayList<>(cells.values());
    }

    /**
     * Returns a list of TableCells representing each cell in the table row.
     *
     * @return a list of TableCells.
     */
    public List<TableCell> getTableCells() {
        List<TableCell> listOfCells = new ArrayList<>();

        // Since we have a LinkedHashMap, the order of entrySet() is guaranteed.
        for (Map.Entry<String, String> mapEntry : cells.entrySet()) {
            TableCell cell = new TableCell(mapEntry.getKey(), mapEntry.getValue());
            listOfCells.add(cell);
        }

        return listOfCells;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cells.hashCode();
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
        TableRow other = (TableRow) obj;
        return Objects.equals(cells, other.cells);
    }
}
