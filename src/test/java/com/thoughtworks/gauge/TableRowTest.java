/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TableRowTest {

    @Test
    public void testToString() {
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");

        assertEquals("TableRow{cells={hello=world, foo=bar}}", row.toString());
    }

    @Test
    public void testEqualSameValuesAndOrder() {
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");

        TableRow otherRow = new TableRow();
        otherRow.addCell("hello", "world");
        otherRow.addCell("foo", "bar");
        assertEquals(row, otherRow);
    }

    @Test
    public void testEqualSameValuesDifferentOrder() {
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");

        TableRow otherRow = new TableRow();
        otherRow.addCell("foo", "bar");
        otherRow.addCell("hello", "world");
        assertEquals(row, otherRow);
    }

    @Test
    public void testEqualDifferentValues() {
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");

        TableRow otherRow = new TableRow();
        otherRow.addCell("foo1", "bar1");
        otherRow.addCell("hello1", "world1");
        assertNotEquals(row, otherRow);
    }
}
