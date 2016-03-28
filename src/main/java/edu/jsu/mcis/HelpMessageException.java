package edu.jsu.mcis;

import java.util.*;

/** Handles usage information for the program and its arguments.
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class HelpMessageException extends RuntimeException{
	
	/**	Assigns the message.
	*	@param msg The usage information.
	*/
	public HelpMessageException(String msg){
		super(msg);
	}
}

