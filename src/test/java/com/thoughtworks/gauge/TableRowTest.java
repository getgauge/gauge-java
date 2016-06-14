package com.thoughtworks.gauge;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class TableRowTest {

    @Test
    public void testToString(){
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");
        
        assertEquals("TableRow{cells={hello=world, foo=bar}}",row.toString());
    }
}
