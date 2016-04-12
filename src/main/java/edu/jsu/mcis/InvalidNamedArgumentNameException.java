package edu.jsu.mcis;

/** Case where named arguments that do not exist are referenced. 
*	

*/	
public class InvalidNamedArgumentNameException extends RuntimeException {
	private String argName;
	
	/** Assigns the exception message and the name that was entered.
	*	@param msg The exception message.
	*	@param name The invalid argument name.
	*/
    public InvalidNamedArgumentNameException(String msg, String name) {
		super(msg + name);
		argName = name;
    }
	
	/**	Gives the error-causing name.
	* 	@return The invalid argument's name.
	*/
	public String getName(){
		return argName;
	}
}