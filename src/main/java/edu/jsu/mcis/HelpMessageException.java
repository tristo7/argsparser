package edu.jsu.mcis;

import java.util.*;

public class HelpMessageException extends RuntimeException{
	private String message;
	private String programName;
	private String programDescription = "";
	private String formattedArgNames = "";
	private String formattedArgDescription = "";

	public HelpMessageException(String prgmName, String prgmDesc, List<String> argNames, Map<String, Arg> argumentMap){
		programName = prgmName;
		programDescription = prgmDesc;
		
		for(int i = 0; i < argNames.size(); i++){
			String s = argNames.get(i);
			formattedArgNames += s + " ";
			formattedArgDescription += s + " " + argumentMap.get(s).getDescription()+"\n";
			
		}
	}
	
	public String getMessage(){
		return "usage: java "+programName+" "+formattedArgNames+"\n"+
					programDescription+"\n"+
					"positional arguments:\n"+
					formattedArgDescription;
	}
}

