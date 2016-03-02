package edu.jsu.mcis;

public class Arg {
    public enum DataType {INTEGER, FLOAT, BOOLEAN, STRING};
    private String argumentName;
    private Object val;
    private DataType dType = DataType.STRING;
	private String argumentDescription = "";

    public Arg(String name) {
        argumentName = name;
    }

    public Arg(String name, DataType type) {
		this(name);
		dType = type;
    }
	
	public Arg(String name, DataType type, String desc) {
        this(name, type);
		argumentDescription = desc;
    }
	
	public void setDescription(String s){
		argumentDescription = s;
	}
	
	public String getDescription(){
		return argumentDescription;
	}
	
	public String getArgName(){
		return argumentName;
	}

	public String getDataType(){
		return dType.toString().toLowerCase();
	}
	
    protected void setVal(String value) {
		switch(dType){
			case INTEGER:
				val = Integer.valueOf(value);
				break;
			case FLOAT:
				val = Float.valueOf(value);
				break;
			case BOOLEAN:
				switch(value.toLowerCase()){
					case "true":
					case "false":
						val = Boolean.valueOf(value);
						break;
					default:
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