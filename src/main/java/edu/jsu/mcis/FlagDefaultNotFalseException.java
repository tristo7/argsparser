package edu.jsu.mcis;

import java.util.*;

/** Case where a boolean named argument is not assigned "false" by default. Boolean named arguments are considered flags and should all be false by default.
*	

*/	
public class FlagDefaultNotFalseException extends RuntimeException {
	private String argName, argValue;
	
	/**	Assigns the message, the argument name, and the invalid value.
	*	@param msg The RunetimeException message.
	*	@param name The named argument that caused the exception.
	*	@param value Value given to argument.
	*/	
	public FlagDefaultNotFalseException(String msg, String name, String value) {
		super(msg + name + ": invalid default value: " + value);
		argName = name;
		argValue = value;
	}	
	
	/**@return The name of the argument that caused the exception.
	*/
	public String getName(){
		return argName;
	}
	
	/**@return The value of the argument that caused the exception.
	*/
	public String getValue(){
		return argValue;
	}
}