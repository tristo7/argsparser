package edu.jsu.mcis;

import java.util.*;

/** Handles when a named argument with data type boolean is not false by default. Boolean named arguments are considered flags and should all be false by default.
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
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
	public String getArgName(){
		return argName;
	}
	
	/**@return The value of the argument that caused the exception.
	*/
	public String getArgValue(){
		return argValue;
	}
}