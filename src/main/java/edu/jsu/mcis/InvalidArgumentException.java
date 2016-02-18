package edu.jsu.mcis;

public class InvalidArgumentException extends RuntimeException {
    private Arg argument;
    private String message;

    public InvalidArgumentException(Arg arg) {
        argument = arg;
    }

    public String getMessage() {
        String dataType = argument.getDataType();
        message = "usage: java VolumeCalculator length width height\n" +
                  "VolumeCalculator.java: error: argument " + argument.argumentName + ": invalid "+dataType+" value: "+argument.getVal(); // add rest of message
		return message;
    }

}