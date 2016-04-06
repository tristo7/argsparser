package edu.jsu.mcis;

/** Throws an exception in the case that too many arguments are given.
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class TooManyArgumentsException extends RuntimeException {
	private String extraArgs;
	
	/**	Assigns the message and additional arguments.
	*	@param msg The exception message.
	*	@param args The remaining arguments formatted as a string.
	*/
	public TooManyArgumentsException(String msg, String args) {
		super(msg + args);
		extraArgs = args;
	}	
	
	/**	Gives the extra arguments formatted into a String.
	*	@return The extra arguments.
	*/
	public String getExtraArgs(){
		return extraArgs;
	}
}
