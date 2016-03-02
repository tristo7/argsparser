package edu.jsu.mcis;

import java.util.*;

public class TooManyArgumentsException extends RuntimeException {
	private String extraArgs, message;
	
	public TooManyArgumentsException(String msg, String args) {
		extraArgs = args;
		message = msg + extraArgs;
	}	
	
	public String getExtraArgs(){
		return extraArgs;
	}
	
	public String getMessage(){
		return message;
	}
}
