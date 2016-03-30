package edu.jsu.mcis;

import java.util.*;

/** Argument class. Holds information about either positional or named arguments.	
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class Arg {
	/** 
	*	Data types that are supported.
	*/
    public enum DataType {INTEGER, FLOAT, BOOLEAN, STRING};
    
	private String argumentName;
	private char argumentShortName;
    private Object val;
    private DataType dType = DataType.STRING;
	private String argumentDescription = "";
	private boolean isOptionalArgument = false;
	private boolean isRestricted = false;
	private List<String> restrictedValues;
	
	/** Constructor that sets the name of the argument.
	*	@param name The name of the argument. 
	*/	
    public Arg(String name) {
        argumentName = name;
    }
	
	/** Constructor that sets the name and data type of the argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	*/	
    public Arg(String name, DataType type) {
		this(name);
		dType = type;
    }
	
	/** Constructor that sets the name, data type, and description of the argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
	*/	
	public Arg(String name, DataType type, String desc) {
        this(name, type);
		argumentDescription = desc;
    }
	
	public Arg(String name, DataType type, String desc, List<String> restrictedValues) {
        this(name, type, desc);
		isRestricted = true;
		this.restrictedValues = restrictedValues;
    }
	
	/** Constructor that sets the name, data type, description, and default value of the argument. <STRONG>Using this constructor indicates a named argument.</STRONG>
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
	*	@param defaultValue The argument's default value entered as a string.
	*/	
	public Arg(String name, DataType type, String desc, String defaultValue){
		this(name, type, desc);
		isOptionalArgument = true;
		setArgValue(defaultValue);
	}
	
	/** Constructor that sets the name, data type, description, default value, and restricted values of the argument. <STRONG>Using this constructor indicates a named argument.</STRONG>
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
	*	@param defaultValue The argument's default value entered as a string.
	*	@param restrictedValues List of the values the argument should be restricted to take on.
	*/	
	public Arg(String name, DataType type, String desc, String defaultValue, List<String> restrictedValues){
		this(name, type, desc);
		isOptionalArgument = true;
		isRestricted = true;
		this.restrictedValues = restrictedValues;
		setArgValue(defaultValue);
	}
	
	public void setRestrictedValues(List<String> values){
		restrictedValues = values;
		isRestricted = true;
	}
	
	public String getRestrictedValues(){
		if(isRestricted)
			return restrictedValues.toString();
		else
			throw new RuntimeException("This is not a restricted argument.");
	}
	
	/** Sets the description of the argument.
	*	@param s Description of the argument.
	*/	
	public void setArgDescription(String s){
		argumentDescription = s;
	}
	
	/** Sets the short form name of a <STRONG>named argument</STRONG>.
	*	@param c Character used to identify the named argument.
	*/	
	public void setArgShortName(char c){
		if(isOptionalArgument)
			argumentShortName = c;
		else
			throw new InvalidArgumentException(argumentName + " is a positional argument.", this);
	}
	
	/**
	*	Gives the argument's descipriton.
	*	@return String value of the argument's descipriton.
	*/	
	public String getArgDescription(){
		return argumentDescription;
	}
	
	/**
	*	Gives the argument's name.
	*	@return String value of the argument's name.
	*/	
	public String getArgName(){
		return argumentName;
	}
	
	/**
	*	Gives the named argument's short form name.
	*	@return character value of the named argument's short form name.
	*/	
	public char getArgShortName(){
		if(isOptionalArgument)
			if(argumentShortName != '\u0000')
				return argumentShortName;
			else
				throw new InvalidArgumentException(argumentName + " does not have a shortname.", this);
		else
			throw new InvalidArgumentException(argumentName + " is not a named argument.", this);
	}
	
	/**
	*	Returns the argument's data type as a String.
	* 	@return String value of the argument's data type.
	*/	
	public String getDataType(){
		return dType.toString().toLowerCase();
	}

    protected void setArgValue(String value) {
		if(isRestricted && !restrictedValues.contains(value)){
			throw new RestrictedValuesException(argumentName + " has a restricted set of values: ", this, value, restrictedValues);
		}
			
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
	
	/** Returns the value of the argument as a generic type. Result must be cast to the proper data type.
	*		Example cases: int i = p.getArgValue() or (int) p.getArgValue();
	*	@param <T> Generic type for the argument. 
	*	@return The argument's value as a generic T type. This should then be cast to the proper data type.
	*/
    public <T> T getArgValue() {
        return (T) val;
    }
	
	/** 
	*	Returns all data stored about the argument in an XML format.
	*	@return XML data formatted as a string.
	*/
	public String toXML(){
		String statement = "";
		if(isOptionalArgument){
			statement += "<named>\n";
		} else {
			statement += "<positional>\n";
		}
		
		
		statement += "    <name>" + argumentName + "</name>\n" +
					 "    <type>" + dType.toString().toLowerCase() + "</type>\n";
		if(isRestricted){
			statement += "    <restrictedvalues>" + restrictedValues.toString().replace("[","").replace("]","") + "</restrictedvalues>\n";
		}
		if(!argumentDescription.equals(""))
			statement += "    <description>" + argumentDescription + "</description>\n";
		if(isOptionalArgument){
			if(argumentShortName != '\u0000'){
				statement += "    <shortname>" + argumentShortName + "</shortname>\n";
			}
			statement += "    <default>" + String.valueOf(val) + "</default>\n</named>\n";
		} else {
			statement += "</positional>\n";
		}
		return statement;
	}
}