package edu.jsu.mcis;

import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/** Creates arguments, parses the command line, and returns their values. <p>
*		The major functions of this class are to properly add arguments and parse the command line 
*			(or any other String array). Other functions exist to return information about the argument(s) or the program.
*		
*		Example code using ArgsParser:
*		<pre>
*		{@code
*			public static void main(String[] args) {
*				ArgsParser p = new ArgsParser();
*				p.setProgramName("VolumeCalculator");
*				p.setProgramDescription("Calculate the volume of a cube, cylinder, or sphere.");
*				p.addArg("length", Arg.DataType.FLOAT, "the length of the box (float)");
*				p.addNamedArg("digits", Arg.DataType.INTEGER, "2", 'd');
*				p.parse(args);
*			}
*		}
*		</pre>
*		<pre>
*		Example command line arguments:
*		VolumeCalculator 7.5 2.4 --type cylinder 8.2 --digits 3
*		</pre>	
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/
public class ArgsParser {

	private List<String> argNames;
	private List<String> namedArgNames;
	private Map<String, Arg> argMap;
	private Map<Character, String> shortNameMap;
	private Map<String[], Boolean> mutualExclusionMap;
	private String programName = "";
	private String programDescription = "";
	private Map<String, Boolean> requiredMap;
	
	/** Default constructor.
	*	Initializes new HashMaps and ArrayLists for storing Args.
	*/
	public ArgsParser() {
		argNames = new ArrayList<String>();
		namedArgNames = new ArrayList<String>();
		argMap = new HashMap<String, Arg>();
		shortNameMap = new HashMap<Character, String>();
		mutualExclusionMap = new HashMap<String[], Boolean>();
		requiredMap = new HashMap<String, Boolean>();
	}
	
	/**	Adds a group of named arguments that are mutually exclusive. No more than one of the named arguments in the group should be present in the command line.
	*	@param arguments a string array with all the names of arguments desired to be mutually exclusive.
	*/
	public void addMutualExclusion(String[] arguments){
		for(String s : arguments){
			if(!namedArgNames.contains(s))
				throw new InvalidNamedArgumentNameException(createExceptionMessage("InvalidNamedArgumentNameException"), s);
		}
		mutualExclusionMap.put(arguments, false);
	}
	
	/**	Gives a List of all mutual exclusion (String arrays) that exists for the ArgsParser instance. <p>
	*		An example List would look like the following: [[1, 2, 3], [4, 5, 6]]
	*	@return a List of String[] that contains argument names.
	*/
	public List<String[]> getMutualExclusion(){		
		List<String[]> list = new ArrayList<String[]>();
		for(String[] s : mutualExclusionMap.keySet()){
			list.add(s);
		}
		return list;
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
	public List<String> getNamedArgumentNames(){
		return namedArgNames;
	}
	
	/**
	*	Gives the number of Args that ArgsParser has stored.
	*	@return the number of Args currently being stored by ArgsParser.
	*/
	public int getNumArguments() {
		return argNames.size()+namedArgNames.size();
	}
	
	/**
	*	Sets the program name based upon a string from the user.
	*	@param name the name for the program
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
	*	@param s the string for the description of the program using ArgsParser.
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
	*	Adds an argument with a name.
	*	@param name the string for the name of the argument to be added.
	*/
	public void addArg(String name) {
		argNames.add(name);
		argMap.put(name, new Arg(name));
	}
	
	/**
	*	Adds an argument with a name and description.
	*	@param name the string for the name of the argument to be added.
	*	@param description the description of the argument to be added.
	*/
	public void addArg(String name, String description) {
		argNames.add(name);
		argMap.put(name, new Arg(name, Arg.DataType.STRING, description));
	}
	
	/**
	*	Adds an argument with a name and data type.
	*	@param name the string for the name of the argument to be added.
	*	@param myType the datatype of the argument to be added.
	*/
	public void addArg(String name, Arg.DataType myType) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType));
	}
	
	/**
	*	Adds an argument with a name, data type, and description.
	*	@param name the string for the name of the argument to be added.
	*	@param myType the datatype of the argument to be added.
	*	@param description the description of the argument to be added.
	*/
	public void addArg(String name, Arg.DataType myType, String description) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType, description));
	}
	
	/**
	*	Adds an argument with a name, data type, description, and restricted values.
	*	@param name the string for the name of the argument to be added.
	*	@param myType the datatype of the argument to be added.
	*	@param description the description of the argument to be added.
	*	@param restrictedValues List of the values the argument should be restricted to take on.
	*/
	public void addArg(String name, Arg.DataType myType, String description, List<String> restrictedValues) {
		argNames.add(name);
		argMap.put(name, new Arg(name, myType, description, restrictedValues));
	}
	
	public void setNamedArgToRequired(String name) {
		if(namedArgNames.contains(name)) {
			requiredMap.put(name, false);
			getArg(name).setToRequired();
		}
		else {
			throw new InvalidNamedArgumentNameException(createExceptionMessage("InvalidNamedArgumentNameException"), name);
		}
	}
	
	public String getRequiredNamedArgs() {
		String requiredArgs = "";
		if(requiredMap.get(name)) {
			for(String s : requiredMap) {
				requiredArgs += s + ", ";
			}
			requiredArgs = requiredArgs.substring(0, requiredArgs.length - 2);
		}
		return requiredArgs;
	}
	
	/**
	*	Adds a named argument with a name, data type, and default value.
	* 	@param name String name of the named argument.
	*	@param type DataType of the argument being added.
	*	@param defaultValue the default value for the argument being created.
	*/
	public void addNamedArg(String name, Arg.DataType type, String defaultValue){
		switch(type){
			case BOOLEAN:
				if(!defaultValue.toLowerCase().equals("false"))
					throw new FlagDefaultNotFalseException(createExceptionMessage("FlagDefaultNotFalseException"),name, defaultValue);
			case INTEGER:
			case STRING:
			case FLOAT:
				namedArgNames.add(name);
				argMap.put(name, new Arg(name, type, "", defaultValue));
				break;
		}
	}
	
	/**
	*	Adds a named argument with a name, data type, default value, short name, and restricted values.
	* 	@param name String name of the named argument.
	*	@param type DataType of the argument being added.
	*	@param defaultValue the default value for the argument being created.
	*	@param shortName the character for the short name of the argument.
	*	@param restrictedValues List of the values the argument should be restricted to take on.
	*/
	public void addNamedArg(String name, Arg.DataType type, String defaultValue, char shortName, List<String> restrictedValues){
		switch(type){
			case BOOLEAN:
				throw new RuntimeException("Boolean arguments do not need restricted values. They are either true of false.\n Argument name: " + name);
			case INTEGER:
			case STRING:
			case FLOAT:
				namedArgNames.add(name);
				argMap.put(name, new Arg(name, type, "", defaultValue, restrictedValues));
				argMap.get(name).setShortName(shortName);
				shortNameMap.put(shortName, name);
				break;
		}
	}
	
	/**
	*	Adds a named argument with a name, data type, default value, and restricted values.
	* 	@param name String name of the named argument.
	*	@param type DataType of the argument being added.
	*	@param defaultValue the default value for the argument being created.
	*	@param restrictedValues List of the values the argument should be restricted to take on.
	*/
	public void addNamedArg(String name, Arg.DataType type, String defaultValue, List<String> restrictedValues){
		switch(type){
			case BOOLEAN:
				throw new RuntimeException("Boolean arguments do not need restricted values. They are either true of false.\n Argument name: " + name);
			case INTEGER:
			case STRING:
			case FLOAT:
				namedArgNames.add(name);
				argMap.put(name, new Arg(name, type, "", defaultValue, restrictedValues));
				break;
		}
	}
	
	/**
	*	Adds an named argument to the HashMap and name, shortname to the list of names, shortnames
	* 	@param name String name of the named argument.
	*	@param type DataType of the argument being added.
	*	@param defaultValue the default value for the argument being created.
	*	@param shortName the character for the short name of the argument.
	*/
	public void addNamedArg(String name, Arg.DataType type, String defaultValue, char shortName){
		shortNameMap.put(shortName, name);
		addNamedArg(name, type, defaultValue);
		argMap.get(name).setShortName(shortName);
	}

	/**
	*	Parses the data passed from the command line and stores arguments.
	* 	@param cla String array of the command line arguments.
	*/
	public void parse(String[] cla) {
		String currentArg = "";
		int currentPosArg = 0;
		String extraArgs = "";
		String missingArgs = "";
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
					argMap.get(argNames.get(currentPosArg)).setValue(currentArg);
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
		if(currentPosArg < argNames.size()) {
			while(currentPosArg < argNames.size()) {
				missingArgs +=" " + argNames.get(currentPosArg);
				currentPosArg++;
			}
		}
		if(!requiredMap.isEmpty()) {
			for(String s : requiredMap.keySet()) {
				if(!requiredMap.get(s)) {
					missingArgs +=" " + s;
				}
			}
		}
		if(!missingArgs.equals("")) {
			throw new TooFewArgumentsException(createExceptionMessage("TooFewArgumentsException"), missingArgs);
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
					throw new InvalidNamedArgumentNameException(createExceptionMessage("InvalidNamedArgumentNameException"), String.valueOf(currentShortArg));
				}
			}
		}
	}
	
	private void dashedArgumentHandler(String namedArgName, Queue<String> q){
		if(!namedArgNames.contains(namedArgName)){
			throw new InvalidNamedArgumentNameException(createExceptionMessage("InvalidNamedArgumentNameException"), namedArgName);
		}else if(!mutualExclusionMap.isEmpty()){
			for(String[] s : mutualExclusionMap.keySet()){
				if(Arrays.asList(s).contains(namedArgName)){
					if(mutualExclusionMap.get(s))
						throw new MutualExclusionException(createExceptionMessage("MutualExclusionException"), namedArgName, s);
					else
						mutualExclusionMap.put(s,true);
				}
			}
		}
		if(!requiredMap.isEmpty()) {
			if(requiredMap.containsKey(namedArgName)) {
				requiredMap.put(namedArgName, true);
			}
		}
		if(argMap.get(namedArgName).getDataType().equals("boolean")){
			argMap.get(namedArgName).setValue("true");
		} else {		
				try{
					argMap.get(namedArgName).setValue(q.element());
					q.remove();
				}catch(NumberFormatException n){
					throw new InvalidArgumentException(createExceptionMessage("InvalidNamedArgumentException"), argMap.get(namedArgName), q.remove());
				}
		}
	}
	
	/**
	*	Gives an Arg based upon the given String name.
	* 	@param name Name of the argument.
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
	*	@param <T> Generic type.
	*	@param name Name of the argument.
	*	@return type T representation of the argument's associated value given the name.
	*/	
	public <T> T getValue(String name) {
		return (T) getArg(name).getValue();
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
			case "FlagDefaultNotFalseException":
			case "InvalidNamedArgumentException":
			case "MutualExclusionException":
				msg += "named argument ";
				break;
			case "InvalidNamedArgumentNameException":
				msg += "named argument name: ";
				break;
			case "TooManyArgumentsException":
				msg += "unrecognized arguments: ";
				break;
			case "TooFewArgumentsException":
				msg += "more arguments are necessary: ";
		}
		return msg;
	}
}