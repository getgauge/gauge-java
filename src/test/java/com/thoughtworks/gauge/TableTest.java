package com.thoughtworks.gauge;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableTest {

    private Table table;

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
    public void shouldGetValuesForGivenColumnNames() {
        List<String> columnValues = table.getColumnValues("col1");

        assertEquals(2, columnValues.size());
        assertTrue(columnValues.contains("foo1"));
        assertTrue(columnValues.contains("foo2"));
    }

    @Test
    public void shouldGetEmptyValuesListForInvalidColumnName() {
        List<String> columnValues = table.getColumnValues("col3");

        assertTrue(columnValues.isEmpty());
    }
}