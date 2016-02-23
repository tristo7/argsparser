package edu.jsu.mcis;

import java.util.*;

public class TooManyArgumentsException extends RuntimeException {
	private String extraArgs;
	private String message;
	private String programName;
	private String formattedArgNames = "";
	
	public TooManyArgumentsException(String s, String name, List<String> argNames) {
		extraArgs = s;
		programName = name;
		
		for(int i = 0; i < argNames.size(); i++){
			formattedArgNames += argNames.get(i) + " ";
		}
	}	
	
	public String getExtraArgs(){
		return extraArgs;
	}
	
	public String getMessage(){
		return "usage: java " + programName + " " + formattedArgNames + "\n"+ programName +".java: error: unrecognized arguments: "+extraArgs;
	}
}
