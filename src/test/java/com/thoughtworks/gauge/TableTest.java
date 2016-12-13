package com.thoughtworks.gauge;

import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void shouldBeAbleToGetTableCellFromColumnName(){
        String cellValue = table.getTableRows().get(0).getCell("col1");
        assertEquals("foo1",cellValue);
        cellValue = table.getTableRows().get(0).getCell("col2");
        assertEquals("bar1",cellValue);
        cellValue = table.getTableRows().get(1).getCell("col1");
        assertEquals("foo2",cellValue);
        cellValue = table.getTableRows().get(1).getCell("col2");
        assertEquals("bar2",cellValue);
        cellValue = table.getTableRows().get(0).getCell("invalid");
        assertEquals("", cellValue);
    }
    
    @Test
    public void testToString(){
        List<String> expectedLines = Arrays.asList("|col1|col2|","|----|----|", "|foo1|bar1|","|foo2|bar2|");
        String expected = Joiner.on(System.getProperty("line.separator")).join(expectedLines);
        
        assertEquals(expected,table.toString());
    }
    
    @Test
    public void testToStringWithEmptyHeaderAndRow(){
        assertEquals("",new Table(new ArrayList<String>()).toString());
    }
}