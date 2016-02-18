package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	private List<String> optionalArgNames;
	private Map<String, Arg> argMap;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		optionalArgNames = new ArrayList<String>();
		argMap = new HashMap<String, Arg>();
	}
	
	public int getNumArguments() {
		return argNames.size()+optionalArgNames.size();
	}
	
	
	public void addArg(String name) {
		argNames.add(name);
		argMap.put(name, new Arg(name));
	}
	
	public void addArg(String name, Arg.DataType myType) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType));
	}
	
	public void addOptionalArg(String name, Arg.DataType type, String defaultValue){
		optionalArgNames.add(name);
		argMap.put(name, new Arg(name, type));
		getArg(name).setVal(defaultValue);
	}
	
	
	public void parse(String[] cla) {
		String temp = "";
		int currentArg = 0;
		int currentPositionInCLA = 0;
		int maxArgs = argNames.size() + (2*optionalArgNames.size());
		String optionalArgName = "";
		String extraArgs = "";
		boolean looping = true;
		System.out.println("cla.length: " + cla.length);
		while(looping){
			System.out.println("current pos in cla: " + currentPositionInCLA);
			if(currentPositionInCLA < cla.length) {
				temp = cla[currentPositionInCLA];
				if(temp.equals("-h")) {
					looping = false;
					throw new HelpMessageException();
					
					//condition for too many arguments... difficult to figure out due to optional arguments being a thing. Not working properly.
				}else if(currentPositionInCLA >= maxArgs){
					looping = false;
					extraArgs = temp;
					currentPositionInCLA++;
					while(currentPositionInCLA < cla.length){
						extraArgs+=" "+cla[currentPositionInCLA];
						currentPositionInCLA++;
					}
					throw new TooManyArgumentsException(extraArgs);
					
				}else if(temp.contains("--")){
					System.out.println("optional arg!");
					optionalArgName = temp.substring(2);
					argMap.get(optionalArgName).setVal(cla[currentPositionInCLA+1]);
					currentPositionInCLA+=2;
					
				}else{
					System.out.println("arg: "+argNames.get(currentArg));
					argMap.get(argNames.get(currentArg)).setVal(temp);
					currentPositionInCLA++;
					currentArg++;
				}
			}else{
				looping = false;
			}
		}
		
	}
	
	public Arg getArg(String name) {
		return argMap.get(name);
	}
	
	public <T> T getArgValue(String name) {
		return (T) argMap.get(name).getVal();
	}
	
}