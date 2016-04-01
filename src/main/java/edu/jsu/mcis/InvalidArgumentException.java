package edu.jsu.mcis;

import java.util.*;

/** Case where invalid values are assigned to positional arguments. Also deals with invalid function calls on arguments.
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class InvalidArgumentException extends RuntimeException {
    private Arg argument;
	
	/** Handles invalid values.
	*	@param msg The message to be assigned to the exception.
	*	@param arg The argument that has an error.
	*	@param value The incorrect value that was assigned to the argument.
	*/	
    public InvalidArgumentException(String msg, Arg arg, String value) {
		super(msg + arg.getArgName() + ": invalid " + arg.getDataType() + " value: " + value);
        argument = arg;
    }
	
	/** Handles invalid function calls.
	*	@param msg The message to be assigned to the exception.
	*	@param arg The argument that has an error.
	*/	
	public InvalidArgumentException(String msg, Arg arg){
		super(msg);
		argument = arg;
	}

	/** Gives the argument that caused the exception.
	*	@return The argument that had the exception. 
	*/	
    public Arg getArgument(){
		return argument;
    }
}