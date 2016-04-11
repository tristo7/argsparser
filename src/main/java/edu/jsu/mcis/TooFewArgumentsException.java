package edu.jsu.mcis;

import java.util.*;

public class TooFewArgumentsException extends RuntimeException {	
	public TooFewArgumentsException(String msg, String args) {
		super(msg + args);
	}
}