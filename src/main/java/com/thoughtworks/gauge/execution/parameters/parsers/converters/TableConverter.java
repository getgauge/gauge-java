package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.gauge.Table;

import gauge.messages.Spec;

public class TableConverter implements StringToPrimitiveConverter {

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
