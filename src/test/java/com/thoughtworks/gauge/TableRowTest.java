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

import org.junit.Test;

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
        assertTrue(row.equals(otherRow));
    }

    @Test
    public void testEqualSameValuesDifferentOrder() {
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");

        TableRow otherRow = new TableRow();
        otherRow.addCell("foo", "bar");
        otherRow.addCell("hello", "world");
        assertTrue(row.equals(otherRow));
    }

    @Test
    public void testEqualDifferentValues() {
        TableRow row = new TableRow();
        row.addCell("hello", "world");
        row.addCell("foo", "bar");

        TableRow otherRow = new TableRow();
        otherRow.addCell("foo1", "bar1");
        otherRow.addCell("hello1", "world1");
        assertFalse(row.equals(otherRow));
    }
}
