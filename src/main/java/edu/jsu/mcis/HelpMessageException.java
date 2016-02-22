package edu.jsu.mcis;

import java.util.*;

public class HelpMessageException extends RuntimeException{
	private String message;
	private String programName;
	private String formattedArgNames = "";

	public HelpMessageException(String name, List<String> argNames){
		programName = name;
		
		for(int i = 0; i < argNames.size(); i++){
			formattedArgNames += argNames.get(i) + " ";
		}
	}
	
	public String getMessage(){
		return "usage: java "+programName+" "+formattedArgNames+"\n"+
					"Calcuate the volume of a box.\n"+
					"positional arguments:\n"+
					"length the length of the box (float)\n"+
					"width the width of the box(float)\n"+
					"height the height of the box(float)";
	}
}

