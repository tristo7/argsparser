package edu.jsu.mcis;

public class Arg {
    public enum DataType {INTEGER, FLOAT, BOOLEAN, STRING};
    private String argumentName;
    private Object val;
    private DataType dType;

    public Arg(String name) {
        argumentName = name;
        dType = DataType.STRING;
    }

    public Arg(String name, DataType type) {
        argumentName = name;
        if(type == DataType.INTEGER) {
            dType = DataType.INTEGER;
        }
        else if(type == DataType.FLOAT) {
            dType = DataType.FLOAT;
        }
        else if(type == DataType.BOOLEAN) {
            dType = DataType.BOOLEAN;
        }
        else if(type == DataType.STRING) {
            dType = DataType.STRING;
        }
    }

    public void setVal(String value) {
        // TODO 
        if(dType == DataType.INTEGER) {
            val = Integer.valueOf(value);
        }
        else if(dType == DataType.FLOAT) {
            val = Float.valueOf(value);
        }
        else if(dType == DataType.BOOLEAN) {
            val = Boolean.valueOf(value);
        }
        else if(dType == DataType.STRING) {
            val = String.valueOf(value);
        }
    }

    public Object getVal() {
        return val;
    }
}