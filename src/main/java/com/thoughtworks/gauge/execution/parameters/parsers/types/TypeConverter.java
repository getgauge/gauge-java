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

package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.Table;
import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;

interface StringToPrimitiveConverter {
    Object convert(Spec.Parameter source) throws Exception;
}

class StringToIntegerConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Integer.parseInt(source.getValue());
    }
}

class StringToBooleanConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Boolean.parseBoolean(source.getValue());
    }
}

class StringToDoubleConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Double.parseDouble(source.getValue());
    }
}

class StringToLongConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Long.parseLong(source.getValue());
    }
}

class StringToFloatConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Float.parseFloat(source.getValue());
    }
}

class TableConverter implements StringToPrimitiveConverter {

    public Object convert(Spec.Parameter source) {
        Spec.ProtoTable protoTable = source.getTable();
        return tableFromProto(protoTable);
    }

    private Object tableFromProto(Spec.ProtoTable protoTable) {
        if (protoTable.getHeaders() == null) {
            throw new RuntimeException("Invalid table passed");
        }
        Spec.ProtoTableRow headerRow = protoTable.getHeaders();
        List<String> headers = getTableRowFor(headerRow);
        Table table = new Table(headers);

        for (int i = 0; i < protoTable.getRowsCount(); i++) {
            Spec.ProtoTableRow protoRow = protoTable.getRows(i);
            table.addRow(getTableRowFor(protoRow));
        }
        return table;

    }

    private List<String> getTableRowFor(Spec.ProtoTableRow tableRow) {
        List<String> row = new ArrayList<String>();
        for (int i = 0; i < tableRow.getCellsCount(); i++) {
            row.add(tableRow.getCells(i));
        }
        return row;
    }
}
