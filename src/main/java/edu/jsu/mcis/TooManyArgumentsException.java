package edu.jsu.mcis;

import java.util.*;

public class TooManyArgumentsException extends RuntimeException {
	private String extraArgs;
	private String message;
	private String programName;
	private List<String> argNames;
	private String formattedArgNames = "";
	
	public TooManyArgumentsException(String s, String name, List<String> aNames) {
		extraArgs = s;
		argNames = aNames;
		programName = name;
		
		for(int i = 0; i < argNames.size(); i++){
			formattedArgNames += argNames.get(i) + " ";
		}
	}	
	
	public String getExtraArgs(){
		return extraArgs;
	}
	
	public String getMessage(){
		return "usage: java " + programName + " " + formattedArgNames + "\nVolumeCalculator.java: error: unrecognized arguments: "+extraArgs;
	}
}
