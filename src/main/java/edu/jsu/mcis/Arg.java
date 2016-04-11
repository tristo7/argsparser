package edu.jsu.mcis;

import java.util.*;

/** Holds information about the argument. <br>
 *	An argument can be either <STRONG>positional</STRONG> or <STRONG>named</STRONG>. <br>
 *	There is also an option to make both arguments <STRONG>restricted</STRONG>.<p>
 *		<STRONG>Positional</STRONG> arguments are required. These arguments will be core to the program.<br>
 *			An example of a positional argument would be length width and height in a volume calculating program.<p>
 *		<STRONG>Named</STRONG>  arguments are considered named.<br>
 *			An example of a named argument would be numeric precsision in a volume calculating program.<br>
 *				The default precision could be to two decimal places, but have support for more.<p>
 *		<STRONG>Resctricted</STRONG> arguments are limited to a specified set of values. This can be thought of as an enumeration.<br>
 *			An example of a restricted (named) argument would be the shape of an object in a volume calculating program.<br> 
 *				It could be limited to the following list: box, sphere, or a cylinder.
 *	
 *	
 *	@author Tristin Terry
 * 	@author Daniel Hilburn
 * 	@author Thomas Eyler 
 * 	@author Jake Hamby
 * 	@author Amari Richardson
 */	
public class Arg {
	/** 
	*	Supported data types.
	*/
    public enum DataType {INTEGER, FLOAT, BOOLEAN, STRING};
    
	private String argumentName;
	private char argumentShortName;
    private Object val;
    private DataType dType = DataType.STRING;
	private String argumentDescription = "";
	private boolean isNamedArgument = false;
	private boolean isNamedRequiredArgument = false;
	private boolean isRestricted = false;
	private List<String> restrictedValues;
	
	/** Sets the name of the argument.
	*	@param name The name of the argument. 
	*/	
    public Arg(String name) {
        argumentName = name;
    }
	
	/** Sets the name and data type of the argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	*/	
    public Arg(String name, DataType type) {
		this(name);
		dType = type;
    }
	
	/** Sets the name, data type, and description of the argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
	*/	
	public Arg(String name, DataType type, String desc) {
        this(name, type);
		argumentDescription = desc;
    }
	
	/** Sets the name, data type, description, and default value of the<STRONG> named </STRONG>argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
	*	@param defaultValue The argument's default value entered as a string.
	*/	
	public Arg(String name, DataType type, String desc, String defaultValue){
		this(name, type, desc);
		isNamedArgument = true;
		setValue(defaultValue);
	}
	
	/** Sets the name, data type, description, and restricted values of the<STRONG> resctricted </STRONG>argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
		@param restrictedValues List of the values the argument should be restricted to take on.
	*/	
	public Arg(String name, DataType type, String desc, List<String> restrictedValues) {
		this(name, type, desc);
		isRestricted = true;
		this.restrictedValues = restrictedValues;
    }
	
	/** Sets the name, data type, description, default value, and restricted values of the<STRONG> restricted named </STRONG>argument.
	*	@param name The name of the argument. 
	* 	@param type The data type of the argument.
	* 	@param desc Description of the argument.
			For example, an argument named "length" may have a description "The length of the box."
	*	@param defaultValue The argument's default value entered as a string.
	*	@param restrictedValues List of the values the argument should be restricted to take on.
	*/	
	public Arg(String name, DataType type, String desc, String defaultValue, List<String> restrictedValues){
		this(name, type, desc);
		isNamedArgument = true;
		isRestricted = true;
		this.restrictedValues = restrictedValues;
		setValue(defaultValue);
	}
	
	protected void setToRequired(){
		if(isNamedArgument){
			isNamedRequiredArgument = true;
		}
	}
	
	/** Sets the restricted values of the argument. The argument will be considered<STRONG> restricted </STRONG> after calling this method on it.
	*	@param values List of the values the argument should be restricted to take on.
	*/	
	public void setRestrictedValues(List<String> values){
		if(dType == DataType.BOOLEAN)
			throw new RuntimeException("Boolean arguments do not need restricted values. They are either true of false.\n Argument name: " + argumentName);
		restrictedValues = values;
		isRestricted = true;
	}
	
	/** Gets the restricted values of the argument formatted in a string.<p>
	*		Example output: "[1, 2, 3]"
	*	@return restricted values of the argument.
	*/	
	public String getRestrictedValues(){
		if(isRestricted)
			return restrictedValues.toString();
		else
			throw new RuntimeException(argumentName + " is not a restricted argument.");
	}
	
	/** Sets the description of the argument.
	*	@param s Description of the argument.
	*/	
	public void setDescrption(String s){
		argumentDescription = s;
	}
	
	/** Sets the short form name of a<STRONG> named </STRONG>argument.
	*	@param c Character used to identify the named argument.
	*/	
	public void setShortName(char c){
		if(isNamedArgument)
			argumentShortName = c;
		else
			throw new InvalidArgumentException(argumentName + " is a positional argument.", this);
	}
	
	/**
	*	Gives the argument's description.
	*	@return String value of the argument's description.
	*/	
	public String getDescription(){
		return argumentDescription;
	}
	
	/**
	*	Gives the argument's name.
	*	@return String value of the argument's name.
	*/	
	public String getName(){
		return argumentName;
	}
	
	/**
	*	Gives character that represents the<STRONG> named </STRONG>argument's short name.
	*	@return character value of the named argument's short form name.
	*/	
	public char getShortName(){
		if(isNamedArgument)
			if(argumentShortName != '\u0000')
				return argumentShortName;
			else
				throw new InvalidArgumentException(argumentName + " does not have a shortname.", this);
		else
			throw new InvalidArgumentException(argumentName + " is not a named argument.", this);
	}
	
	/**
	*	Returns the argument's data type as a string in lowercase. Values may be the following: string, integer, boolean, or float.
	* 	@return String value of the argument's data type.
	*/	
	public String getDataType(){
		return dType.toString().toLowerCase();
	}
	
	/**	Sets and stores the value of the argument.
	*	@param value Value to be stored in the argument. It will be parsed and cast to its proper data type.
	*/	
    protected void setValue(String value) {
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
	
	/** Returns the value of the argument as a generic type. Result must be cast to the proper data type. <p>
	*		Example cases: int i = p.getValue("myarg") or (int) p.getValue("myarg"); <br>
	*			Where p is an instance of ArgsParser.
	*	@param <T> Generic type for the argument. 
	*	@return The argument's value as a generic T type. This should then be cast to the proper data type.
	*/
    public <T> T getValue() {
        return (T) val;
    }
	
	/** 
	*	Returns all data stored about the argument in an XML format.
	*	@return XML data formatted as a string.
	*/
	public String toXML(){
		String statement = "";
		if(isNamedArgument){
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
		if(isNamedArgument){
			if(argumentShortName != '\u0000'){
				statement += "    <shortname>" + argumentShortName + "</shortname>\n";
			}
			if(isNamedRequiredArgument)
				statement += "    <required>true</required>";
			statement += "    <default>" + String.valueOf(val) + "</default>\n</named>\n";
			
		} else {
			statement += "</positional>\n";
		}
		return statement;
	}
}