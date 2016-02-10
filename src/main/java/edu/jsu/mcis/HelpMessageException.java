package edu.jsu.mcis;

public class HelpMessageException extends RuntimeException{
	private String message;


	public HelpMessageException(){
		message = "usage: java VolumeCalculator length width height\n"+
					"Calcuate the volume of a box.\n"+
					"positional arguments:\n"+
					"length the length of the box (float)\n"+
					"width the width of the box(float)\n"+
					"height the height of the box(float)";
	}
	
	public String getMessage(){
		return message;
	}
}

