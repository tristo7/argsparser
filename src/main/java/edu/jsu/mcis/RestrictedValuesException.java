package edu.jsu.mcis;

import java.util.*;

/** Case where a value that is not within a restricted set of allowed values is entered.
*	

*/	
public class RestrictedValuesException extends RuntimeException {
    private Arg argument;
	
	/** Basic constructor
	*	@param msg The message to be assigned to the exception.
	*	@param arg The argument that has an error.
	*	@param value The incorrect value that was assigned to the argument.
	*	@param restrictedValues List of the values the argument should be restricted to take on.
	*/
    public RestrictedValuesException(String msg, Arg arg, String value, List<String> restrictedValues) {
		super(msg + restrictedValues + ". " + value +" is not an accepted value.");
		argument = arg;
    }
	
	/** Gives the argument that caused the exception.
	*	@return The argument that had the exception. 
	*/
    public Arg getArgument(){
		return argument;
    }
}