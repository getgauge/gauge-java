package com.thoughtworks.gauge;

public class StepValueExtractor {

    public StepValue getValue(String value) {
        int paramCount = 0;
        StringBuilder extractedValue = new StringBuilder();
        char[] chars = value.toCharArray();
        Boolean inEscape = false;
        boolean inQuotes = false;
        boolean inBracket = false;
        for (char aChar : chars) {
            if (inEscape) {
                inEscape = false;
                if (!inQuotes && !inBracket)
                    extractedValue.append(aChar);
            } else if (aChar == '"') {
                if (!inQuotes) {
                    inQuotes = true;
                } else {
                    extractedValue.append("{}");
                    paramCount++;
                    inQuotes = false;
                }
            } else if (aChar == '<' && !inBracket) {
                inBracket = true;
            } else if (aChar == '>' && inBracket) {
                extractedValue.append("{}");
                paramCount++;
                inBracket = false;
            } else if (aChar == '\\') {
                inEscape = true;
            } else if (!inQuotes && !inBracket) {
                extractedValue.append(aChar);
            }
        }
        return new StepValue(extractedValue.toString(), paramCount);
    }

    public StepValue getValueWithTable(String stepText) {
        StepValue valueWithoutTable = getValue(stepText);
        return new StepValue(valueWithoutTable.getValue() + " {}", valueWithoutTable.getParamCount() + 1);
    }

    public class StepValue {
        private final String value;
        private final int paramCount;

        public StepValue(String value, int paramCount) {
            this.value = value;
            this.paramCount = paramCount;
        }

        public String getValue() {
            return value;
        }

        public int getParamCount() {
            return paramCount;
        }
    }
}
