package edu.jsu.mcis;

import java.util.*;

/** Errors related to XML input/output.
*	

*/	
public class XMLException extends RuntimeException{
	
	/**	Assigns the message.
	*	@param msg The exception message.
	*/
	public XMLException(String msg){
		super (msg);
	}
}