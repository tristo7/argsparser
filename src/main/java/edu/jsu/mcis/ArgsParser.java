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
		String args = "";
		for(int i = 0; i < cla.length; i++) {
			args += cla[i] + " ";
		}
		
		Scanner s = new Scanner(args);
		String temp = "";
		int currentArg = 0;
		String extraArgs = "";
		boolean looping = true;
		
		while(looping){
			if(s.hasNext()) {
				temp = s.next();
				if(temp.equals("-h")) {
					looping = false;
					throw new HelpMessageException();
				}
				else if(getNumArguments() < currentArg + 1){
					looping = false;
					extraArgs = temp;
					while(s.hasNext()){
						extraArgs+=" "+s.next();
					}
					throw new TooManyArgumentsException(extraArgs);
				}
				else {
					argValues.add(temp);
					currentArg++;
				}
			}else{
				s.close();
				looping = false;
			}
		}
		
	}


	public String getArg(String name) {
		return argValues.get(argNames.indexOf(name));
	}
	
}