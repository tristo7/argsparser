package edu.jsu.mcis;

/** Handles cases when named arguments that do not exist are referenced, wether it be by full name or short name. 
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class InvalidOptionalArgumentNameException extends RuntimeException {
	private String argName;
	
	/** Assigns the exception message and the name that put in.
	*	@param msg The exception message.
	*	@param name The invalid argument name.
	*/
    public InvalidOptionalArgumentNameException(String msg, String name) {
		super(msg + name);
		argName = name;
    }
	
	/**	@return The invalid argument's name.
	*/
	public String getArgName(){
		return argName;
	}
}