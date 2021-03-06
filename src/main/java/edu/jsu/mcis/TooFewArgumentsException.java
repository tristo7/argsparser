package edu.jsu.mcis;

import java.util.*;
/** Throws an exception in the case that too few arguments are given.
*	

*/	

public class TooFewArgumentsException extends RuntimeException {	

	/**	Assigns the message.
	*	@param msg The exception message.
	*	@param args The missing arguments formatted as a string.
	*/
	public TooFewArgumentsException(String msg, String args) {
		super(msg + args);
	}

}