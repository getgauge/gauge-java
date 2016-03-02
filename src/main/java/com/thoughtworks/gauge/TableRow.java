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

import java.util.*;

/**
 * Represents a Row of Data in a Table.
 */
public class TableRow{
    private LinkedHashMap<String, String> cells = new LinkedHashMap<String, String>();

    /**
     * Get the value of cell corresponding to a column name.
     * @param columnName - The column name of TableRow.
     * @return The value of cell corresponding to a column name.
     */
    public String getCell(String columnName){
        if (!cells.containsKey(columnName)){
            return "";
        }
        return cells.get(columnName);
    }

    /**
     * Add a cell to the TableRow.
     * @param columnName  The column name against which the cell is added.
     * @param value The value to be stored in the cell.
     */
    public void addCell(String columnName, String value) {
        cells.put(columnName, value);
    }

    /**
     * Get the number of cells in TableRow.
     * @return The number of cells in TableRow.
     */
    public int size() {
        return cells.size();
    }

    @Override
    public String toString() {
        return "TableRow{" +
                "cells=" + cells +
                '}';
    }

    /**
     * Returns a list containing the values of each cell in the table row.
     * @return a list of the values of each cell in the table row.
     */
    public List<String> getCellValues() {
        //Since we have a LinkedHashMap, the order of values() is guaranteed.
        return new ArrayList<String>(cells.values());
    }

    /**
     * Returns a list of TableCells representing each cell in the table row.
     * @return a list of TableCells.
     */
    public List<TableCell> getTableCells() {
        List<TableCell> listOfCells = new ArrayList<TableCell>();

        //Since we have a LinkedHashMap, the order of entrySet() is guaranteed.
        for (Map.Entry<String, String> mapEntry : cells.entrySet()) {
            TableCell cell = new TableCell(mapEntry.getKey(), mapEntry.getValue());
            listOfCells.add(cell);
        }

        return listOfCells;
    }
}
