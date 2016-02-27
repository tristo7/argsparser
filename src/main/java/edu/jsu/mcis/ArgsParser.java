package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	private List<String> optionalArgNames;
	private Map<String, Arg> argMap;
	private String programName = "";
	private String programDescription = "";
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		optionalArgNames = new ArrayList<String>();
		argMap = new HashMap<String, Arg>();
	}
	
	
	public int getNumArguments() {
		return argNames.size()+optionalArgNames.size();
	}
	
	
	public void setProgramName(String name) {
		programName = name;
	}
	
	public String getProgramName() {
		return programName;
	}
	
	
	public void setProgramDescription(String s) {
		programDescription = s;
	}
	
	public String getProgramDescription() {
		return programDescription;
	}
	
	
	public void addArg(String name) {
		argNames.add(name);
		argMap.put(name, new Arg(name));
	}
	
	public void addArg(String name, String description) {
		argNames.add(name);
		argMap.put(name, new Arg(name, Arg.DataType.STRING, description));
	}
	
	public void addArg(String name, Arg.DataType myType) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType));
	}
	
	public void addArg(String name, Arg.DataType myType, String description) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType, description));
	}
	
	public void addOptionalArg(String name, Arg.DataType type, String defaultValue){
		if(type == Arg.DataType.BOOLEAN){
			if(defaultValue.contains("false") || defaultValue.contains("False")){
				optionalArgNames.add(name);
				argMap.put(name, new Arg(name, type));
				getArg(name).setVal(defaultValue);
			} else {
				throw new FlagDefaultNotFalseException(name, defaultValue);
			}
		} else {
			optionalArgNames.add(name);
			argMap.put(name, new Arg(name, type));
			getArg(name).setVal(defaultValue);
		}
	}

	public void parse(String[] cla) {
		String temp = "";
		int currentArg = 0;
		int currentPositionInCLA = 0;
		int maxArgs = argNames.size() + (2*optionalArgNames.size());
		String optionalArgName = "";
		String extraArgs = "";
		boolean looping = true;
		while(looping){
			if(currentPositionInCLA < cla.length) {
				temp = cla[currentPositionInCLA];
				if(temp.equals("-h") || temp.equals("--help")) {
					throw new HelpMessageException(programName, programDescription, argNames, argMap);
					
				}else if(temp.contains("--")){
					optionalArgName = temp.substring(2);
					if(argMap.get(optionalArgName).getDataType().equals("boolean")){
						argMap.get(optionalArgName).setVal("true");
						currentPositionInCLA+=1;
					} else {
						try{
						argMap.get(optionalArgName).setVal(cla[currentPositionInCLA+1]);
						}catch(NumberFormatException n){
							argMap.get(optionalArgName).setValAsString(cla[currentPositionInCLA+1]);
							throw new InvalidArgumentException(argMap.get(optionalArgName), programName, argNames);
						}
						currentPositionInCLA+=2;
					}
					
				}else if(currentArg < argNames.size()){
					try{
						argMap.get(argNames.get(currentArg)).setVal(temp);
					}catch(NumberFormatException n){
						argMap.get(argNames.get(currentArg)).setValAsString(temp);
						throw new InvalidArgumentException(argMap.get(argNames.get(currentArg)), programName, argNames);
					}
					currentPositionInCLA++;
					currentArg++;
					
				}else{
					looping = false;
					extraArgs = temp;
					currentPositionInCLA++;
					while(currentPositionInCLA < cla.length){
						extraArgs+=" "+cla[currentPositionInCLA];
						currentPositionInCLA++;
					}
					throw new TooManyArgumentsException(extraArgs, programName, argNames);
					

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