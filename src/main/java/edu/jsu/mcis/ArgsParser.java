package edu.jsu.mcis;

import java.util.*;

public class ArgsParser {
	private List<String> argPositionsByName;
	private List<Integer> argPosition;
	
	public ArgsParser() {
		argMap = new Hashtable<String, Arg>();
		argPositionsByName = new ArrayList<String>();
	}
	
	public int getNumArguments() {
		return argPositionsByName.length;
	}
	
	public void addArg(String name) {
		argMap.put(name, new Arg(name));
		argPositionsByName.add(name);
	}
	
	public void parse(String cla) {
		Scanner s = new Scanner(cla);
		int position = 0;
		if(s.hasNext()) {
			if(s.findInLine("-h") == "-h") {
				getHelpMessage();
				
			}
			else {
				while(s.hasNext()) {
					argMap.get(argPositionsByName.get(position)).setValue(s.next());
				}
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