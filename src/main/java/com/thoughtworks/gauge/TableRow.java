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
     * The method converts our table row to an array list.
     * @return an array list containing the String cell values.
     */
    public List<String> getCellValues() {
        //The LinkedHaspMap inherits the values() method, which returns a Collection view of
        //    the values contained in the LinkedHaspMap
        Collection<String> values = cells.values();

        return new ArrayList<String>(values);
    }

    /**
     * This method returns a list of cell objects that contain a value with its associated
     * column name.
     * @return listOfCells  a list containing our cell objects.
     */
    public List<TableCell> getTableCells() {
        List<TableCell> listOfCells = new ArrayList<TableCell>();

        for (Map.Entry<String, String> mapEntry : cells.entrySet()) {
            TableCell cell = new TableCell(mapEntry.getValue(), mapEntry.getKey());
            listOfCells.add(cell);
        }

        return listOfCells;
    }
}
