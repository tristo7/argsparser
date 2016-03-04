package edu.jsu.mcis;

import java.util.*;

public class InvalidOptionalArgumentNameException extends RuntimeException {
	private String argName;

    public InvalidOptionalArgumentNameException(String msg, String name) {
		super(msg + name);
		argName = name;
    }
	
	public String getArgName(){
		return argName;
	}
}