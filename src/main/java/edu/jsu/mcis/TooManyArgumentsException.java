package edu.jsu.mcis;

import java.util.*;

public class TooManyArgumentsException extends RuntimeException {
	private String extraArgs;
	
	public TooManyArgumentsException(String msg, String args) {
		super(msg + args);
		extraArgs = args;
	}	
	
	public String getExtraArgs(){
		return extraArgs;
	}
}
