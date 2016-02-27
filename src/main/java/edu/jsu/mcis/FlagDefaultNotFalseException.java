package edu.jsu.mcis;

import java.util.*;

public class FlagDefaultNotFalseException extends RuntimeException {
	private String argName, val;
	
	public FlagDefaultNotFalseException(String name, String value) {
		argName = name;
		val = value;
	}	
	
	public String getMessage(){
		return "Argument " + argName + " has an invalid default value of "+val+".\n Change the default value to false.";
	}
}