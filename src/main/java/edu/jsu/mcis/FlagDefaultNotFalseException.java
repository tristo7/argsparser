package edu.jsu.mcis;

import java.util.*;

public class FlagDefaultNotFalseException extends RuntimeException {
	private String argName, argValue;
	
	public FlagDefaultNotFalseException(String msg, String name, String value) {
		super(msg + name + ": invalid default value: " + value);
		argName = name;
		argValue = value;
	}	
	
	public String getArgName(){
		return argName;
	}
	
	public String getArgValue(){
		return argValue;
	}
}