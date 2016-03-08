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
        return "TableCell{" +
                "columnName=" + columnName +
                ", " +
                "value=" + value +
                '}';
    }
}