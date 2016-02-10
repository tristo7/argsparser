package edu.jsu.mcis;

public class TooManyArgumentsException extends RuntimeException {
	private String extraArgs;
	private String message;
	
	public TooManyArgumentsException(String s) {
		extraArgs = s;
	}	
	
	public String getExtraArgs(){
		return extraArgs;
	}
	
	public String getMessage(){
		return "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: unrecognized arguments: "+extraArgs;
	}
}
