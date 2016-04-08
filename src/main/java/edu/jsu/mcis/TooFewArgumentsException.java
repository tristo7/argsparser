package edu.jsu.mcis;

import java.util.*;

public class TooFewArgumentsException extends RuntimeException {
	private String missingArgs;
	
	public TooFewArgumentsException(String msg, String args) {
		super(msg + args);
		missingArgs = args;
	}
	
	public String getMissingArgs() {
		return missingArgs;
	}
}