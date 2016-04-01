package edu.jsu.mcis;

import java.util.*;

/** Usage information for the program.
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

