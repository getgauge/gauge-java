/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.google.common.base.Joiner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    private Table table;

    @BeforeEach
    public void setUp() throws Exception {
        List<String> headers = new ArrayList<>();
        headers.add("col1");
        headers.add("col2");
        table = new Table(headers);

        List<String> row1 = new ArrayList<>();
        row1.add("foo1");
        row1.add("bar1");

        List<String> row2 = new ArrayList<>();
        row2.add("foo2");
        row2.add("bar2");

        table.addRow(row1);
        table.addRow(row2);
    }

    @Test
    public void shouldGetListOfColumnNames() {
        List<String> columnNames = table.getColumnNames();

        assertEquals(2, columnNames.size());
        assertEquals("col1", columnNames.get(0));
        assertEquals("col2", columnNames.get(1));
    }

    @Test
    public void shouldGetAColumnNameByIndex() {
        String columnName = table.getColumnName(0);
        assertEquals("col1", columnName);
        columnName = table.getColumnName(1);
        assertEquals("col2", columnName);
    }

    @Test
    public void shouldThrowAnExceptionWhenInvalidColumnIndex() {
        int invalidColumnIndex = 3;

        assertThatThrownBy(() -> table.getColumnName(invalidColumnIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining(String.format("Column with index %d not found. Actual column size: %d.", invalidColumnIndex, table.getColumnNames().size()));
    }

    @Test
    public void shouldGetValuesForGivenColumnNames() {
        List<String> columnValues = table.getColumnValues("col1");

        assertEquals(2, columnValues.size());
        assertTrue(columnValues.contains("foo1"));
        assertTrue(columnValues.contains("foo2"));
    }

    @Test
    public void shouldGetValuesForGivenColumnIndex() {
        List<String> columnValues = table.getColumnValues(1);

        assertEquals(2, columnValues.size());
        assertTrue(columnValues.contains("bar1"));
        assertTrue(columnValues.contains("bar2"));
    }

    @Test
    public void shouldGetEmptyValuesListForInvalidColumnName() {
        List<String> columnValues = table.getColumnValues("col3");

        assertTrue(columnValues.isEmpty());
    }

    @Test
    public void shouldBeAbleToGetTableCellFromColumnName() {
        String cellValue = table.getTableRows().get(0).getCell("col1");
        assertEquals("foo1", cellValue);
        cellValue = table.getTableRows().get(0).getCell("col2");
        assertEquals("bar1", cellValue);
        cellValue = table.getTableRows().get(1).getCell("col1");
        assertEquals("foo2", cellValue);
        cellValue = table.getTableRows().get(1).getCell("col2");
        assertEquals("bar2", cellValue);
        cellValue = table.getTableRows().get(0).getCell("invalid");
        assertEquals("", cellValue);
    }

    @Test
    public void testToString() {
        List<String> expectedLines = Arrays.asList("|col1|col2|", "|----|----|", "|foo1|bar1|", "|foo2|bar2|");
        String expected = Joiner.on(System.lineSeparator()).join(expectedLines);

        assertEquals(expected, table.toString());
    }

    @Test
    public void testToStringWithEmptyHeaderAndRow() {
        assertEquals("", new Table(new ArrayList<>()).toString());
    }

    @Test
    public void testEqualsWithSameHeadersOrderAndValues() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2");
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertEquals(table, otherTable);
    }

    @Test
    public void testEqualsWithDifferentHeadersOrderSameHeadersValues() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col2", "col1");
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertFalse(table.equals(otherTable));
    }

    @Test
    public void testEqualsWithDifferentHeadersValues() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col11", "col2");
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertNotEquals(table, otherTable);
    }

    @Test
    public void testEqualsWithDifferentHeadersSize() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2", "col3");
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertNotEquals(table, otherTable);
    }

    @Test
    public void testEqualWithTableHavingSameHeadersAndValues() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2");
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList("value11", "value12"));
        table.addRow(Arrays.asList("value21", "value22"));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList("value11", "value12"));
        otherTable.addRow(Arrays.asList("value21", "value22"));
        assertEquals(table, otherTable);
    }

    @Test
    public void testEqualHavingMatchingHeadersMatchingValuesInDifferentOrder() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2");
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList("value11", "value12"));
        table.addRow(Arrays.asList("value21", "value22"));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList("value21", "value22"));
        otherTable.addRow(Arrays.asList("value11", "value12"));

        assertNotEquals(table, otherTable);
    }

    @Test
    public void testEqualWithPartialMatchingValues() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2");
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList("value11", "value12"));
        table.addRow(Arrays.asList("value21", "value22"));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList("value11", "value12"));
        otherTable.addRow(Arrays.asList("value21", "value22"));
        otherTable.addRow(Arrays.asList("value31", "value32"));
        assertNotEquals(table, otherTable);
    }

    @Test
    public void testEqualWithJavaRules() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2");
        List<String> other1TableHeaders = Arrays.asList("col1", "col2");

        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList("value11", "value12"));
        table.addRow(Arrays.asList("value21", "value22"));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList("value11", "value12"));
        otherTable.addRow(Arrays.asList("value21", "value22"));

        Table other1Table = new Table(other1TableHeaders);
        other1Table.addRow(Arrays.asList("value11", "value12"));
        other1Table.addRow(Arrays.asList("value21", "value22"));

        // Reflexivity
        assertEquals(table, table);
        // Symmetry
        assertEquals(table, otherTable);
        assertEquals(otherTable, table);
        // Transitivity
        assertEquals(otherTable, other1Table);
        assertEquals(table, other1Table);
        // Non-nullity
        assertNotEquals(null, table);
    }

    @Test
    public void testEqualsTwoTablesSameValuesDifferentOrder() {
        List<String> tableHeaders = Arrays.asList("col1", "col2");
        List<String> otherTableHeaders = Arrays.asList("col1", "col2");
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList("A", "B"));
        table.addRow(Arrays.asList("C", "D"));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList("C", "D"));
        otherTable.addRow(Arrays.asList("A", "B"));

        assertNotEquals(table, otherTable);
    }
}
