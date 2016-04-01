package edu.jsu.mcis;

/** Case where named arguments that do not exist are referenced. 
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class InvalidOptionalArgumentNameException extends RuntimeException {
	private String argName;
	
	/** Assigns the exception message and the name that was entered.
	*	@param msg The exception message.
	*	@param name The invalid argument name.
	*/
    public InvalidOptionalArgumentNameException(String msg, String name) {
		super(msg + name);
		argName = name;
    }
	
	/**	Gives the error-causing name.
	* 	@return The invalid argument's name.
	*/
	public String getArgName(){
		return argName;
	}
}