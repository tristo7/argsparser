package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	private List<String> argValues;
	private int numArgCounter;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		argValues = new ArrayList<Integer>();
		numArgCounter = 0;
	}
	
	public int getNumArguments() {
		return numArgCounter;

	private List<String> argPositionsByName;
	private List<Integer> argPosition;
	
	public ArgsParser() {
		argMap = new Hashtable<String, Arg>();
		argPositionsByName = new ArrayList<String>();
	}
	
	public int getNumArguments() {
		return argPositionsByName.length;

	}
	
	public void addArgName(String name) {
		argNames.add(name);
	}
	
	
	public void parse(String cla) {
		Scanner s = new Scanner(cla);
		int position = 0;
		if(s.hasNext()) {
			if(s.findInLine("-h") == "-h") {
				getHelpMessage();
				
			}
			else {
				/*while(s.hasNext()) {
					
				}*/
			}
		}
		s.close();
	}
	

	public int getArg(String name) {
		return ;
	}
	
	public void getHelpMessage() {
		System.out.println("usage: java VolumeCalculator length width height" + "\n"
							+ "Calculate the volume of a box." + "\n"
							+ "positional arguments:" + "\n"
							+ "\t" + "length the length of the box" + "\n"
							+ "\t" + "width the width of the box" + "\n"
							+ "\t" + "height the height of the box");
	}
}