package edu.jsu.mcis;

import java.util.*;

/** Errors related to mutual exclusion. Possible cause: calling getMutualExclusion() without having set mutual exclusion. 
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class MutualExclusionException extends RuntimeException{
	
	/**	Assigns the message with the argumentName that caused the excpection and a list of arguments that are in the set.
	*	@param msg The usage information.
	*	@param argumentName name of the exception causing argument
	*	@param exclusionSet mutually exclusive set of argument names.
	*/
	public MutualExclusionException(String msg, String argumentName, String[] exclusionSet){
		super(msg + argumentName + " is in the mutually exclusive set " + Arrays.toString(exclusionSet) + ".");
	}
}

