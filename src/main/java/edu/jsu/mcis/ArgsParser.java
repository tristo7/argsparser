package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	private List<String> argValues;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		argValues = new ArrayList<String>();
		printHelp = false;
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
		if(s.hasNext()) {
			if(s.findInLine("-h") == "-h") {
				System.out.println(getHelpMessage());
				System.exit(0);
			}
			else if(getNumArguments() < currentArg + 1){
				do{
					extraArgs=extraArgs+" "+s.next();
				}while(s.hasNext());
				throw new TooManyArgumentsException(extraArgs);
			}
			else {
				while(s.hasNext()) {
					temp = s.next();
					argValues.add(temp);
				}
			}
		}
		s.close();
	}


	public String getArg(String name) {
		return argValues.get(argNames.indexOf(name));
	}
	
	public String getHelpMessage() {
		return "usage: java VolumeCalculator length width height" + "\n"
							+ "Calculate the volume of a box." + "\n"
							+ "positional arguments:" + "\n"
							+ "\t" + "length the length of the box" + "\n"
							+ "\t" + "width the width of the box" + "\n"
							+ "\t" + "height the height of the box";
	}
	
	public String getOutOfBoundValues() {
		return "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: unrecognized arguments: ";
	}
}