package edu.jsu.mcis;

public class Arg {
    public enum DataType {INTEGER, FLOAT, BOOLEAN, STRING};
    String argumentName;
    private Object val;
    private DataType dType;

    public Arg(String name) {
        argumentName = name;
        dType = DataType.STRING;
    }

    public Arg(String name, DataType type) {
        argumentName = name;
		dType = type;
    }
	
	public String getDataType(){
		switch(dType){
			case INTEGER:
				return "integer";
			case FLOAT:
				return "float";
			case BOOLEAN:
				return "boolean";
			default:
				return "string";
        }
	}
	
    protected void setVal(String value) {
        // TODO 
		switch(dType){
			case INTEGER:
				val = Integer.valueOf(value);
				break;
			case FLOAT:
				val = Float.valueOf(value);
				break;
			case BOOLEAN:
				val = Boolean.valueOf(value);
				break;
			default:
				val = value;
        }
    }

    public <T> T getVal() {
        return (T) val;
    }
}