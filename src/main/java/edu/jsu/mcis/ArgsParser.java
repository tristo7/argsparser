package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {

	private List<String> argNames;
	private List<Float> argValues;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		argValues = new ArrayList<Float>();
	}
	
	public int getNumArguments() {
		return argNames.size();
	}
	
	
	public void addArg(String name) {
		argNames.add(name);
	}
	
	
	public void parse(String cla) {
		Scanner s = new Scanner(cla);
		String temp = "";
		float tempFloat = 0;
		if(s.hasNext()) {
			if(s.findInLine("-h") == "-h") {
				getHelpMessage();
			}
			else {
				while(s.hasNext()) {
					temp = s.next();
					tempFloat = Float.valueOf(temp);
					argValues.add(tempFloat);
				}
			}
		}
		s.close();
	}
	

	public float getArg(String name) {
		return argValues.get(argNames.indexOf(name));
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