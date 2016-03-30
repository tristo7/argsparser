package edu.jsu.mcis;

import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/** Parser class for the Arguments
*	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/
public class ArgsParser {

	private List<String> argNames;
	private List<String> optionalArgNames;
	private Map<String, Arg> argMap;
	private String programName = "";
	private String programDescription = "";
	private Map<Character, String> shortNameMap;
	
	/** Constructor that creates a new instance of ArgsParser.
	*	Initializes new HashMaps and ArrayLists for storing Args.
	*/
	public ArgsParser() {
		argNames = new ArrayList<String>();
		optionalArgNames = new ArrayList<String>();
		argMap = new HashMap<String, Arg>();
		shortNameMap = new HashMap<Character, String>();
	}
	
	/**
	*	Gives the List of names of positional Args that ArgsParser has stored.
	*	@return the List of names of positional Args currently being stored by ArgsParser.
	*/
	public List<String> getPositionalArgumentNames(){
		return argNames;
	}
	
	/**
	*	Gives the List of names of named Args that ArgsParser has stored.
	*	@return the List of names of named Args currently being stored by ArgsParser.
	*/
	public List<String> getOptionalArgumentNames(){
		return optionalArgNames;
	}
	
	/**
	*	Gives the number of Args that ArgsParser has stored.
	*	@return the number of Args currently being stored by ArgsParser.
	*/
	public int getNumArguments() {
		return argNames.size()+optionalArgNames.size();
	}
	
	/**
	*	Sets the program name based upon a string from the user.
	*	@param the name for the program
	*/	
	public void setProgramName(String name) {
		programName = name;
	}
	
	/**
	*	Gives the program name specified by the user.
	*	@return string containing the name of the program using ArgsParser
	*/
	public String getProgramName() {
		return programName;
	}
	
	/**
	*	Sets the program description based upon a string from the user.
	*	@param the string for the description of the program using ArgsParser.
	*/	
	public void setProgramDescription(String s) {
		programDescription = s;
	}
	
	
	/**
	*	Gives the program description specified by the user.
	*	@return string containing the description of the program using ArgsParser
	*/	
	public String getProgramDescription() {
		return programDescription;
	}
	
	/**
	*	Method to add an argument with the name of the argument.
	*	@param the string for the name of the argument to be added.
	*/
	public void addArg(String name) {
		argNames.add(name);
		argMap.put(name, new Arg(name));
	}
	
	/**
	*	Method to add an argument with the name of the argument and description.
	*	@param the string for the name of the argument to be added.
	*	@param the description of the argument to be added.
	*/
	public void addArg(String name, String description) {
		argNames.add(name);
		argMap.put(name, new Arg(name, Arg.DataType.STRING, description));
	}
	
	/**
	*	Method to add an argument with the name of the argument and description.
	*	@param the string for the name of the argument to be added.
	*	@param the datatype of the argument to be added.
	*/
	public void addArg(String name, Arg.DataType myType) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType));
	}
	
	/**
	*	Method to add an argument with the name of the argument and description.
	*	@param the string for the name of the argument to be added.
	*	@param the datatype of the argument to be added.
	*	@param the description of the argument to be added.
	*/
	public void addArg(String name, Arg.DataType myType, String description) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType, description));
	}
	
	/**
	*	Adds an optional argument to the HashMap without the shortname
	* 	@param String name of the optional argument.
	*	@param DataType of the argument being added.
	*	@param the default value for the argument being created.
	*/
	public void addOptionalArg(String name, Arg.DataType type, String defaultValue){
		switch(type){
			case BOOLEAN:
				if(!defaultValue.toLowerCase().equals("false"))
					throw new FlagDefaultNotFalseException(createExceptionMessage("FlagDefaultNotFalseException"),name, defaultValue);
			case INTEGER:
			case STRING:
			case FLOAT:
				optionalArgNames.add(name);
				argMap.put(name, new Arg(name, type, "", defaultValue));
				break;
		}
	}
	
	/**
	*	Adds an optional argument to the HashMap and name, shortname to the list of names, shortnames
	* 	@param String name of the optional argument.
	*	@param DataType of the argument being added.
	*	@param the default value for the argument being created.
	*	@param the character for the short name of the argument
	*/
	public void addOptionalArg(String name, Arg.DataType type, String defaultValue, char shortName){
		shortNameMap.put(shortName, name);
		addOptionalArg(name, type, defaultValue);
		getArg(name).setArgShortName(shortName);
	}

	/**
	*	Parses the data passed from the command line and stores arguments.
	* 	@param String array of the command line arguments
	*/
	public void parse(String[] cla) {
		String currentArg = "";
		int currentPosArg = 0;
		String extraArgs = "";
		Queue<String> arguments = new LinkedList<String>();
		
		for(int i = 0;i<cla.length;i++){
			arguments.add(cla[i]);
		}
		while(!arguments.isEmpty()){
			currentArg = arguments.remove();
			if(currentArg.contains("-")){
				dashedArgumentClassifier(currentArg, arguments);					
			}else if(currentPosArg < argNames.size()){
				try{
					argMap.get(argNames.get(currentPosArg)).setArgValue(currentArg);
				}catch(NumberFormatException n){
					throw new InvalidArgumentException(createExceptionMessage("InvalidArgumentException"), argMap.get(argNames.get(currentPosArg)), currentArg);
				}
				currentPosArg++;
			}else{
				extraArgs = currentArg;
				while(!arguments.isEmpty()){
					extraArgs+=" "+arguments.remove();
				}
				throw new TooManyArgumentsException(createExceptionMessage("TooManyArgumentsException"), extraArgs);
			}
		}
		
	}
	
	private void dashedArgumentClassifier(String t, Queue<String> q) {
		if(t.equals("-h") || t.equals("--help")){
				throw new HelpMessageException(createExceptionMessage("HelpMessageException"));
		}else if(t.contains("--")){
			dashedArgumentHandler(t.substring(2), q);
		} else {
			for(int i = 1;i<t.length();i++){
				char currentShortArg = t.charAt(i);
				if(shortNameMap.containsKey(currentShortArg)){
					dashedArgumentHandler(shortNameMap.get(t.charAt(i)), q);
				}else{
					throw new InvalidOptionalArgumentNameException(createExceptionMessage("InvalidOptionalArgumentNameException"), String.valueOf(currentShortArg));
				}
			}
		}
	}
	
	private void dashedArgumentHandler(String optionalArgName, Queue<String> q){
		if(!optionalArgNames.contains(optionalArgName)){
			throw new InvalidOptionalArgumentNameException(createExceptionMessage("InvalidOptionalArgumentNameException"), optionalArgName);
		}else if(argMap.get(optionalArgName).getDataType().equals("boolean")){
			argMap.get(optionalArgName).setArgValue("true");
		} else {		
				try{
					argMap.get(optionalArgName).setArgValue(q.element());
					q.remove();
				}catch(NumberFormatException n){
					throw new InvalidArgumentException(createExceptionMessage("InvalidOptionalArgumentException"), argMap.get(optionalArgName), q.remove());
				}
		}
	}
	
	/**
	*	Gives an Arg based upon the given String name.
	*	@return Arg object from the argMap given the name of the Arg.
	*/	
	public Arg getArg(String name) {
		if(argMap.containsKey(name))
			return argMap.get(name);
		else
			throw new RuntimeException("Argument " + name + " does not exist.");
	}
	
	/**
	*	Gives the value of an argument cast as type T.
	*	@return type T representation of the argument's associated value given the name.
	*/	
	public <T> T getArgValue(String name) {
		return (T) getArg(name).getArgValue();
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
					msg += argNames.get(i) + " " + argMap.get(argNames.get(i)).getArgDescription() + "\n";
				}
				break;
			case "InvalidArgumentException":
				msg += "argument ";
				break;
			case "FlagDefaultNotFalseException":
			case "InvalidOptionalArgumentException":
				msg += "optional argument ";
				break;
			case "InvalidOptionalArgumentNameException":
				msg += "optional argument name: ";
				break;
			case "TooManyArgumentsException":
				msg += "unrecognized arguments: ";
		}
		return msg;
	}
}