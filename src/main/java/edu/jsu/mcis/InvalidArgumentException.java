package edu.jsu.mcis;

public class InvalidArgumentException {
    private Arg argument;
    private String message;

    public InvalidArgumentException(Arg arg) {
        argument = arg;
    }

    private String getMessage() {
        String dataType = argument.getDataType;
        message = "usage: java VolumeCalculator length width height\n" +
                  "error: argument: " + argument.argumentName; // add rest of message
    }

}