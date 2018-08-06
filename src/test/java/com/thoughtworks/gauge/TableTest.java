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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.base.Joiner;

public class TableTest {

    private Table table;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        List<String> headers = new ArrayList<String>();
        headers.add("col1");
        headers.add("col2");
        table = new Table(headers);

        List<String> row1 = new ArrayList<String>();
        row1.add("foo1");
        row1.add("bar1");

        List<String> row2 = new ArrayList<String>();
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
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage(String.format("Column with index %d not found. Actual column size: %d.", invalidColumnIndex, table.getColumnNames().size()));

        table.getColumnName(invalidColumnIndex);
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
        String expected = Joiner.on(System.getProperty("line.separator")).join(expectedLines);

        assertEquals(expected, table.toString());
    }

    @Test
    public void testToStringWithEmptyHeaderAndRow() {
        assertEquals("", new Table(new ArrayList<String>()).toString());
    }

    @Test
    public void testEqualsWithSameHeadersOrderAndValues() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertTrue(table.equals(otherTable));
    }

    @Test
    public void testEqualsWithDifferentHeadersOrderSameHeadersValues() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col2", "col1" });
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertTrue(!table.equals(otherTable));
    }

    @Test
    public void testEqualsWithDifferentHeadersValues() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col11", "col2" });
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertFalse(table.equals(otherTable));
    }

    @Test
    public void testEqualsWithDifferentHeadersSize() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2", "col3" });
        Table table = new Table(tableHeaders);
        Table otherTable = new Table(otherTableHeaders);
        assertFalse(table.equals(otherTable));
    }

    @Test
    public void testEqualWithTableHavingSameHeadersAndValues() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        table.addRow(Arrays.asList(new String[] { "value21", "value22" }));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        otherTable.addRow(Arrays.asList(new String[] { "value21", "value22" }));
        assertTrue(table.equals(otherTable));
    }

    @Test
    public void testEqualHavingMatchingHeadersMatchingValuesInDifferentOrder() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        table.addRow(Arrays.asList(new String[] { "value21", "value22" }));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList(new String[] { "value21", "value22" }));
        otherTable.addRow(Arrays.asList(new String[] { "value11", "value12" }));

        assertFalse(table.equals(otherTable));
    }

    @Test
    public void testEqualWithPartialMatchingValues() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        table.addRow(Arrays.asList(new String[] { "value21", "value22" }));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        otherTable.addRow(Arrays.asList(new String[] { "value21", "value22" }));
        otherTable.addRow(Arrays.asList(new String[] { "value31", "value32" }));
        assertFalse(table.equals(otherTable));
    }

    @Test
    public void testEqualWithJavaRules() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> other1TableHeaders = Arrays.asList(new String[] { "col1", "col2" });

        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        table.addRow(Arrays.asList(new String[] { "value21", "value22" }));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        otherTable.addRow(Arrays.asList(new String[] { "value21", "value22" }));

        Table other1Table = new Table(other1TableHeaders);
        other1Table.addRow(Arrays.asList(new String[] { "value11", "value12" }));
        other1Table.addRow(Arrays.asList(new String[] { "value21", "value22" }));

        // Reflexivity
        assertTrue(table.equals(table));
        // Symmetry
        assertTrue(table.equals(otherTable));
        assertTrue(otherTable.equals(table));
        // Transitivity
        assertTrue(otherTable.equals(other1Table));
        assertTrue(table.equals(other1Table));
        // Non-nullity
        assertFalse(table.equals(null));
    }

    @Test
    public void testEqualsTwoTablesSameValuesDifferentOrder() {
        List<String> tableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        List<String> otherTableHeaders = Arrays.asList(new String[] { "col1", "col2" });
        Table table = new Table(tableHeaders);
        table.addRow(Arrays.asList(new String[] { "A", "B" }));
        table.addRow(Arrays.asList(new String[] { "C", "D" }));

        Table otherTable = new Table(otherTableHeaders);
        otherTable.addRow(Arrays.asList(new String[] { "C", "D" }));
        otherTable.addRow(Arrays.asList(new String[] { "A", "B" }));

        assertFalse(table.equals(otherTable));
    }
}