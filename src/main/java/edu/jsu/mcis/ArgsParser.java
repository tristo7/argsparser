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
		String optionalArgName = "";
		String extraArgs = "";
		boolean looping = true;
		Queue<String> arguments = new LinkedList<String>();
		for(int i = 0;i<cla.length;i++){
			arguments.add(cla[i]);
		}
		
		while(looping){
			if(!arguments.isEmpty()) {
				temp = arguments.remove();
				if(temp.equals("-h") || temp.equals("--help")) {
					String s = createExceptionMessage("HelpMessageException");
					throw new HelpMessageException(s);
					
				}else if(temp.contains("--")){
					optionalArgName = temp.substring(2);
					if(argMap.get(optionalArgName).getDataType().equals("boolean")){
						argMap.get(optionalArgName).setVal("true");
					} else {
						
						try{
						argMap.get(optionalArgName).setVal(arguments.element());
						arguments.remove();
						}catch(NumberFormatException n){
							throw new InvalidArgumentException(createExceptionMessage("InvalidArgumentException"), argMap.get(optionalArgName), arguments.remove());
						}
					}
					
				}else if(currentArg < argNames.size()){
					try{
						argMap.get(argNames.get(currentArg)).setVal(temp);
					}catch(NumberFormatException n){
						throw new InvalidArgumentException(createExceptionMessage("InvalidArgumentException"), argMap.get(argNames.get(currentArg)), temp);
					}
					currentArg++;
					
				}else{
					looping = false;
					extraArgs = temp;
					
					while(!arguments.isEmpty()){
						extraArgs+=" "+arguments.remove();
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
	
	private String createExceptionMessage(String messageType){
		String msg = "usage: java "+programName+" ";
		for(int i = 0; i<argNames.size();i++){
			msg += argNames.get(i) + " ";
		}
		
		msg +="\n"+programName+".java: error: ";
		
		switch(messageType){
			case "HelpMessageException":
				msg = msg.substring(0, msg.length()-7);
				msg += programDescription + "\npositional arguments:\n";
				for(int i = 0; i < argNames.size(); i++){
					msg += argNames.get(i) + " " + argMap.get(argNames.get(i)).getDescription() + "\n";
				}
				break;
			case "InvalidArgumentException":
				msg += "argument ";
				break;
			case "TooManyArgumentsException":
				break;
			case "FlagDefaultNotFalseException":
				break;
		}
		
		
		return msg;
	}
}