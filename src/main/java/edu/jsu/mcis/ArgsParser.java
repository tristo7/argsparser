package edu.jsu.mcis

import java.util.*;

public class ArgsParser {
	private Hashtable<String, Arg> argMap;
	private List<String> argPositionsByName;
	
	public ArgsPaser() {
		argMap = new Hashtable<String, Arg>();
		argPositionsByName = new ArrayList<String>();
		numArgs = 0;
	}
	
	public int getNumArguments() {
		return numArgs;
	}
	
	public void addArg(String name) {
		argMap.put(name, new Arg(name));
		argPositionsByName.add(name);
	}
	
	public void parse(String cla) {
		Scanner s = new Scanner(cla);
		int position = 0;
		if(s.hasNext()) {
			if(s.findInLine("-h") == "-h" || s.findInLine("--help") == "--help") {
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
	
	public String getArg(String name) {
		return "";
	}
	
	public void getHelpMessage() {
		System.out.println("");
	}
}