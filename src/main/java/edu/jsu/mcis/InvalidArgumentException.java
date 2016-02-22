package edu.jsu.mcis;

import java.util.*;

public class InvalidArgumentException extends RuntimeException {
    private Arg argument;
    private String message;
	private String programName;
	private String formattedArgNames = "";

    public InvalidArgumentException(Arg arg, String name, List<String> argNames) {
        argument = arg;
		programName = name;
		for(int i = 0; i < argNames.size(); i++){
			formattedArgNames += argNames.get(i) + " ";
		}
    }

    public String getMessage() {
        String dataType = argument.getDataType();
        message = "usage: java "+programName+" "+formattedArgNames+"\n" +
                  programName+".java: error: argument " + argument.argumentName + ": invalid "+dataType+" value: "+argument.getVal(); // add rest of message
		return message;
    }

}