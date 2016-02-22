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
	
	protected void setValAsString(String value){
		val = value;
	}
	
    protected void setVal(String value) {
		switch(dType){
			case INTEGER:
				val = Integer.parseInt(value);
				break;
			case FLOAT:
				val = Float.parseFloat(value);
				break;
			case BOOLEAN:
				if(value.equals("true")||value.equals("True")){
					val = true;
				} else if (value.equals("false")||value.equals("False")){
					val = false;
				} else {
					throw new NumberFormatException();
				}
				break;
			default:
				val = value;
        }
    }

    public <T> T getVal() {
        return (T) val;
    }
}