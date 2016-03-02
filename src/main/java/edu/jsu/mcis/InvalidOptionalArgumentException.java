package edu.jsu.mcis;

import java.util.*;

public class InvalidOptionalArgumentException extends RuntimeException {
	private Arg argument;
    private String message;
	private String programName;
	private String formattedArgNames = "";
	private String incorrectArgs;

    public InvalidOptionalArgumentException(String s,Arg arg, String name, List<String> argNames) {
		incorrectArgs = s;
        argument = arg;
		programName = name;
		for(int i = 0; i < argNames.size(); i++){
			formattedArgNames += argNames.get(i) + " ";
		}
    }

	public String getIncorrectArgs(){
		return incorrectArgs;
	}
	
    public String getMessage() {
        String dataType = argument.getDataType();
        message = "usage: java "+programName+" "+formattedArgNames+"\n" +
                  programName+".java: error: optional argument " + argument.argumentName + ": invalid "+dataType+" value: "+argument.getVal(); // add rest of message
		return message;
    }
}