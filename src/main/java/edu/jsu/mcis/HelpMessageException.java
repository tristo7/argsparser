package edu.jsu.mcis;

import java.util.*;

/** Usage information for the program.
*	

*/	
public class HelpMessageException extends RuntimeException{
	
	/**	Assigns the message.
	*	@param msg The usage information.
	*/
	public HelpMessageException(String msg){
		super(msg);
	}
}

