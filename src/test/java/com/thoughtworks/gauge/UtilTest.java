/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

    @Test
    public void testGetCamelCase() throws Exception {
        assertEquals("helloWorld", Util.convertToCamelCase("Hello world"));
        assertEquals("gaugeJavaProject", Util.convertToCamelCase("gauge Java  project "));
        assertEquals("123Go", Util.convertToCamelCase("12   3 go"));
        assertEquals("allhailTheKing", Util.convertToCamelCase("   AllHaiL tHe king"));

    }

    @Test
    public void testGetValidJavaIdentifier() {
        assertEquals("", Util.getValidJavaIdentifier("%"));
        assertEquals("", Util.getValidJavaIdentifier("^%@!*^&|()"));
        assertEquals("", Util.getValidJavaIdentifier("|"));
        assertEquals("hello", Util.getValidJavaIdentifier("hello"));
        assertEquals("hello", Util.getValidJavaIdentifier("hello&"));
        assertEquals("_hello", Util.getValidJavaIdentifier("_hello&"));
        assertEquals("_hello", Util.getValidJavaIdentifier("_hello&("));
        assertEquals("2hello", Util.getValidJavaIdentifier("2hello&("));
        assertEquals("ƒ", Util.getValidJavaIdentifier("˚¬∆˙©ƒ∂"));
    }
}
