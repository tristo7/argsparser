package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	//private List<String> argValues;
	private Map<String, Arg> argMap;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		//argValues = new ArrayList<String>();
		argMap = new HashMap<String, Arg>();
	}
	
	public int getNumArguments() {
		return argNames.size();
	}
	
	
	public void addArg(String name) {
		argNames.add(name);
		argMap.put(name, new Arg(name));
	}
	
	public void addArg(String name, Arg.DataType myType) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType));
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
					//argValues.add(temp);
					argMap.get(argNames.get(currentArg)).setVal(temp);
					currentArg++;
				}
			}else{
				looping = false;
			}
		}
		
	}


	public <T> T getArgValue(String name) {
		return (T) argMap.get(name).getVal();
	}
	
}