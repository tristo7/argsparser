package edu.jsu.mcis;

import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ArgsParser {

	private List<String> argNames;
	private List<String> optionalArgNames;
	private Map<String, Arg> argMap;
	private String programName = "";
	private String programDescription = "";
	private Map<Character, String> shortNameMap;
	
	public ArgsParser() {
		argNames = new ArrayList<String>();
		optionalArgNames = new ArrayList<String>();
		argMap = new HashMap<String, Arg>();
		shortNameMap = new HashMap<Character, String>();
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
		switch(type){
			case BOOLEAN:
				if(!defaultValue.toLowerCase().equals("false"))
					throw new FlagDefaultNotFalseException(createExceptionMessage("FlagDefaultNotFalseException"),name, defaultValue);
			case INTEGER:
			case STRING:
			case FLOAT:
				optionalArgNames.add(name);
				argMap.put(name, new Arg(name, type));
				getArg(name).setVal(defaultValue);
				break;
		}
	}
	
	public void addOptionalArg(String name, Arg.DataType type, String defaultValue, char shortName){
		shortNameMap.put(shortName, name);
		addOptionalArg(name, type, defaultValue);
	}

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
					argMap.get(argNames.get(currentPosArg)).setVal(currentArg);
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
			argMap.get(optionalArgName).setVal("true");
		} else {		
				try{
					argMap.get(optionalArgName).setVal(q.element());
					q.remove();
				}catch(NumberFormatException n){
					throw new InvalidArgumentException(createExceptionMessage("InvalidOptionalArgumentException"), argMap.get(optionalArgName), q.remove());
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
	
	public void loadFromXml(String fileLocation){
		try {
			if(fileLocation.contains(".xml")) {
				File xmlFile = new File(fileLocation);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("positional");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
					}
				}
			}
			else {
				throw new Exception();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void saveToXML(String fileLocation){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element arguments = doc.createElement("arguments");
			//build XML file 
			doc.appendChild(arguments);
			String currentArgName = "";
			for(int i = 0;i<argNames.size();i++){
				currentArgName = argNames.get(i);
				Element positional = doc.createElement("positional");
				arguments.appendChild(positional);
				
				Element name = doc.createElement("name");
				positional.appendChild(name);
				name.appendChild(doc.createTextNode(currentArgName));
				
				Element type = doc.createElement("type");
				positional.appendChild(type);
				type.appendChild(doc.createTextNode(argMap.get(currentArgName).getDataType()));
				
				Element position = doc.createElement("position");
				positional.appendChild(position);
				position.appendChild(doc.createTextNode(Integer.toString(i)));
			}
			
			
			for(int i = 0; i<optionalArgNames.size();i++){
				currentArgName = optionalArgNames.get(i);
				Element named = doc.createElement("named");
				arguments.appendChild(named);
				
				Element name = doc.createElement("name");
				named.appendChild(name);
				name.appendChild(doc.createTextNode(currentArgName));
				//shortname currently broken.
				Element shortName = doc.createElement("shortname");
				named.appendChild(shortName);
				shortName.appendChild(doc.createTextNode("broken"));
				
				Element type = doc.createElement("type");
				named.appendChild(type);
				type.appendChild(doc.createTextNode(argMap.get(currentArgName).getDataType()));
				
				Element defaultValue = doc.createElement("default");
				named.appendChild(defaultValue);
				switch(argMap.get(currentArgName).getDataType()){
					case "string":
						defaultValue.appendChild(doc.createTextNode((String) argMap.get(currentArgName).getVal()));
						break;
					case "boolean":
						defaultValue.appendChild(doc.createTextNode(Boolean.toString(argMap.get(currentArgName).getVal())));
						break;
					case "float":
						defaultValue.appendChild(doc.createTextNode(Float.toString(argMap.get(currentArgName).getVal())));
						break;
					case "integer":
						defaultValue.appendChild(doc.createTextNode(Integer.toString(argMap.get(currentArgName).getVal())));
						break;
				}
			}
			
			//save the xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileLocation));
			
			transformer.transform(source, result);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
}