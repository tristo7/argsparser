package edu.jsu.mcis;

public class Arg {
    public enum DataType {INTEGER, FLOAT, BOOLEAN, STRING};
    private String argumentName;
	private char argumentShortName;
    private Object val;
    private DataType dType = DataType.STRING;
	private String argumentDescription = "";
	private boolean isOptionalArgument = false;
	private int position;

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
	
	public Arg(String name, DataType type, String desc, String defaultValue){
		this(name, type, desc);
		isOptionalArgument = true;
		setVal(defaultValue);
	}
	
	public void setDescription(String s){
		argumentDescription = s;
	}
	
	public void setArgShortName(char c){
		argumentShortName = c;
	}
	
	public String getDescription(){
		return argumentDescription;
	}
	
	public String getArgName(){
		return argumentName;
	}
	
	public char getArgShortName(){
		if(isOptionalArgument)
			return argumentShortName;
		else
			throw new InvalidArgumentException("This is not a named argument.", this);
	}

	public String getDataType(){
		return dType.toString().toLowerCase();
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int pos) {
		pos = position;
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
	
	public String toXML(){
		String statement = "";
		if(isOptionalArgument){
			statement += "<named>\n";
			
		} else {
			statement += "<positional>\n";
		}
		
		statement += "    <name>" + argumentName + "</name>\n" +
					 "    <type>" + dType.toString().toLowerCase() + "</type>\n";
		if(!argumentDescription.equals(""))
			statement += "    <description>" + argumentDescription + "</description>\n";
		
		if(isOptionalArgument){
			if(argumentShortName != '\u0000'){
				statement += "    <shortname>" + argumentShortName + "</shortname>\n";
			}
			statement += "    <default>" + String.valueOf(val) + "</default>\n</named>";
			
		} else {
			statement += "</positional>";
		}
		
		
		return statement;
	}
}