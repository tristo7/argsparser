package edu.jsu.mcis;

import java.util.*;

public class InvalidArgumentException extends RuntimeException {
    private Arg argument;

    public InvalidArgumentException(String msg, Arg arg, String value) {
		super(msg + arg.getArgName() + ": invalid " + arg.getDataType() + " value: " + value);
        argument = arg;
    }

    public Arg getArgument(){
		return argument;
    }
}