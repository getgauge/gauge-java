package com.thoughtworks.gauge;

import main.Messages;

import java.util.ArrayList;
import java.util.List;

interface StringToPrimitiveConverter {
    Object convert(Messages.Argument source);
}

class StringToIntegerConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Messages.Argument source) {
        return Integer.parseInt(source.getValue());
    }
}

class StringToBooleanConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Messages.Argument source) {
        return Boolean.parseBoolean(source.getValue());
    }
}

class StringToDoubleConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Messages.Argument source) {
        return Double.parseDouble(source.getValue());
    }
}

class StringToLongConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Messages.Argument source) {
        return Long.parseLong(source.getValue());
    }
}

class StringToFloatConverter implements StringToPrimitiveConverter {
    @Override
    public Object convert(Messages.Argument source) {
        return Float.parseFloat(source.getValue());
    }
}

class TableConverter implements StringToPrimitiveConverter {

    @Override
    public Object convert(Messages.Argument source) {
        Messages.ProtoTable protoTable = source.getTable();
        return tableFromProto(protoTable);
    }

    private Object tableFromProto(Messages.ProtoTable protoTable) {
        if (protoTable.getRowsCount() == 0) {
            throw new RuntimeException("Invalid table passed");
        }
        Messages.TableRow tableRow = protoTable.getRowsList().get(0);
        List<String> headers = getTableRowFor(tableRow);
        Table table = new Table(headers);

        for (int i = 1; i < protoTable.getRowsCount(); i++) {
            Messages.TableRow protoRow = protoTable.getRows(i);
            table.addRow(getTableRowFor(protoRow));
        }
        return table;

    }

    private List<String> getTableRowFor(Messages.TableRow tableRow) {
        List<String> row = new ArrayList<String>();
        for (int i = 0; i < tableRow.getCellsCount(); i++) {
            row.add(tableRow.getCells(i));
        }
        return row;
    }
}
