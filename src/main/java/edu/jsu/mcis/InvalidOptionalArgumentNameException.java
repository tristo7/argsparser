package edu.jsu.mcis;

import java.util.*;

public class InvalidOptionalArgumentNameException extends RuntimeException {
    private String message;
	private String argName;

    public InvalidOptionalArgumentNameException(String msg, String name) {
		message = msg + name;
		argName = name;
    }
	
	public String getMessage(){
		return message;
	}
	
	public String getArgName(){
		return argName;
	}

}