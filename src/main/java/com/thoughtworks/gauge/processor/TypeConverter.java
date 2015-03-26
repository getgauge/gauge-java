// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.Table;
import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;

interface StringToPrimitiveConverter {
    Object convert(Spec.Parameter source);
}

class StringToIntegerConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Spec.Parameter source) {
        return Integer.parseInt(source.getValue());
    }
}

class StringToBooleanConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Spec.Parameter source) {
        return Boolean.parseBoolean(source.getValue());
    }
}

class StringToDoubleConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Spec.Parameter source) {
        return Double.parseDouble(source.getValue());
    }
}

class StringToLongConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Spec.Parameter source) {
        return Long.parseLong(source.getValue());
    }
}

class StringToFloatConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Spec.Parameter source) {
        return Float.parseFloat(source.getValue());
    }
}

class TableConverter implements StringToPrimitiveConverter {

    @Override
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
