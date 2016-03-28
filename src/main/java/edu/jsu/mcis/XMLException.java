package edu.jsu.mcis;

import java.util.*;

/** Handles exceptions related to XML input/output.
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class XMLException extends RuntimeException{
	
	/**	Assigns the message.
	*	@param msg The exception message.
	*/
	public XMLException(String msg){
		super (msg);
	}
}