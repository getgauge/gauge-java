/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

public class RowSizeMismatchException extends RuntimeException {
    public RowSizeMismatchException(String error) {
        super(error);
    }
}
