package edu.jsu.mcis;

import java.util.*;

public class FlagDefaultNotFalseException extends RuntimeException {
	private String argName, argValue, message;
	
	public FlagDefaultNotFalseException(String msg, String name, String value) {
		argName = name;
		argValue = value;
		message = msg + argName + ": invalid default value: " + argValue;
	}	
	
	public String getMessage(){
		return message;
	}
	
	public String getArgName(){
		return argName;
	}
	
	public String getArgValue(){
		return argValue;
	}
}