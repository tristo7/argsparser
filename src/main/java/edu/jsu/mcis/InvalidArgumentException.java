package edu.jsu.mcis;

import java.util.*;

public class InvalidArgumentException extends RuntimeException {
    private Arg argument;
    private String message;

    public InvalidArgumentException(String msg, Arg arg, String value) {
        argument = arg;
		message = msg;
		message += argument.getArgName() + ": invalid " + argument.getDataType() +
			" value: " + value;
    }

    public Arg getArgument(){
		return argument;
    }
	
	public String getMessage(){
		return message;
	}

}