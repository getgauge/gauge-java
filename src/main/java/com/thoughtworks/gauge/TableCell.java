package com.thoughtworks.gauge;

/**
 * This class represents a single table cell object containing
 * the cell's value and it's associated column name.
 */
public class TableCell {
    private String value;
    private String columnName;

    public TableCell(String cellValue, String columnName) {
        value = cellValue;
        this.columnName = columnName;
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
                "value=" + value + ", " +
                "columnName=" + columnName +
                '}';
    }
}