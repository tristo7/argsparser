package edu.jsu.mcis;

public class XMLTools{
	
	public static void save(ArgsParser p, String fileLocation){
		//convert string returned by Arg's toXML method to an XML doc.
		//use a loop on all existing arguments calling the method.
		//position is not functional as of yet.
	}
	
	public static ArgsParser load(String fileLocation){
		ArgsParser a = new ArgsParser();
		//parse the xml document with a loop that calls addArg & addOptionalArg accordingly.
		//return the resultant instance of ArgsParser.
		return a;
	}
}