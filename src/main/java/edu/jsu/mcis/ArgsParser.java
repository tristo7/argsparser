package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	private List<String> argValues;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		argValues = new ArrayList<String>();
	}
	
	public int getNumArguments() {
		return argNames.size();
	}
	
	
	public void addArg(String name) {
		argNames.add(name);
	}
	
	
	public void parse(String[] cla) {
		String temp = "";
		int currentArg = 0;
		String extraArgs = "";
		boolean looping = true;
		
		while(looping){
			if(currentArg < cla.length) {
				temp = cla[currentArg];
				if(temp.equals("-h")) {
					looping = false;
					throw new HelpMessageException();
				}
				else if(getNumArguments() < currentArg + 1){
					looping = false;
					extraArgs = temp;
					currentArg++;
					while(currentArg < cla.length){
						extraArgs+=" "+cla[currentArg];
						currentArg++;
					}
					throw new TooManyArgumentsException(extraArgs);
				}
				else {
					argValues.add(temp);
					currentArg++;
				}
			}else{
				looping = false;
			}
		}
		
	}


	public String getArg(String name) {
		return argValues.get(argNames.indexOf(name));
	}
	
}