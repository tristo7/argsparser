package edu.jsu.mcis;

import java.util.*;
/** Throws an exception in the case that too few arguments are given.
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	

public class TooFewArgumentsException extends RuntimeException {
	private String missingArgs;

	/**	Assigns the message and additional arguments.
	*	@param msg The exception message.
	*	@param args The missing arguments formatted as a string.
	*/
	public TooFewArgumentsException(String msg, String args) {
		super(msg + args);
		missingArgs = args;
	}
	
	/**	Gives the mssing arguments formatted into a String.
	*	@return The missing arguments.
	*/
	public String getMissingArgs() {
		return missingArgs;
	}
}