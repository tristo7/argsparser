package edu.jsu.mcis;

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/** Allows XML input/output.	User can pass in an XML file for loading arguments, 
*   or save an existing set of arguments from an existing instance of ArgsParser.
*	
*		Example code using ArgsParser:
*		<pre>
*		{@code
*			public static void main(String[] args) {
*				ArgsParser p = new ArgsParser();
*				XMLTools x = new XMLTools();
*				p.setProgramName("VolumeCalculator");
*				p.setProgramDescription("Calculate the volume of a cube, cylinder, or sphere.");
*				p.addArg("length", Arg.DataType.FLOAT, "the length of the box (float)");
*				p.addNamedArg("digits", Arg.DataType.INTEGER, "2", 'd');
*				x.save(p, "testing.xml");
*			}
*		}
*		</pre>
*
*	@author Tristin Terry
* 	@author Daniel Hilburn
* 	@author Thomas Eyler
* 	@author Jake Hamby
* 	@author Amari Richardson
*/	
public class XMLTools{
	
	/** XML save method. This method extracts all known information from Argsparser and formats it in XML.
	*	@param p ArgsParser instance to save from.
	*	@param fileLocation filepath to save the XML file to. Entire path with file extension should be used.
	*/	
	public static void save(ArgsParser p, String fileLocation){
		String xml = "<arguments>\n";
		List<String[]> mutex = p.getMutualExclusion();
		
		if (!p.getProgramName().equals(""))
			xml += "    <programname>" + p.getProgramName() + "</programname>\n";
		if (!p.getProgramDescription().equals(""))
			xml += "    <programdescription>" + p.getProgramDescription() + "</programdescription>\n";
		if (!p.getMutualExclusion().isEmpty()){
			for(String[] s: mutex){
				xml+= "    <mutualexclusion>" + Arrays.toString(s).replace("[","").replace("]","")+"</mutualexclusion>";
			}
		}
		int position = 1;
		for(String s : p.getPositionalArgumentNames()){
			String temp = p.getArg(s).toXML();
			temp = temp.substring(13);
			temp = "<position>" + String.valueOf(position) + "</position>\n" + temp;
			xml += "<positional>\n" + "    " + temp;
			position++;
		}
		for(String s : p.getNamedArgumentNames()){
			xml += p.getArg(s).toXML();
		}
		xml += "</arguments>";
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			InputSource input = new InputSource();
			input.setCharacterStream(new StringReader(xml));
			Document doc = docBuilder.parse(input);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileLocation));
			transformer.transform(source, result);
		}catch(Exception e){
			throw new XMLException(e.getMessage());
		}
	}
	
	/** XML load method. This method extracts information from an XML file and loads it into an ArgsParser.
	*	@param fileLocation Path of the XML file. Entire path with file extension should be used.
	*	@return ArgsParser with data from XML loaded into it. 
	*/	
	public ArgsParser load(String fileLocation){
		ArgsParser a = new ArgsParser();
		UserHandler userH = new UserHandler();
		try {
			if(fileLocation.contains(".xml")) {
				File xmlFile = new File(fileLocation);
				SAXParserFactory spFactory = SAXParserFactory.newInstance();
				SAXParser saxParse = spFactory.newSAXParser();
				saxParse.parse(xmlFile, userH);
				a = userH.getArgsParser();
				return a;
			} else {
				throw new XMLException("The file must have an xml extension.");
			}
		}
		catch(Exception e){
			throw new XMLException(e.getMessage());
		}
	}
	
	private static Arg.DataType typeConversion(String t) {
		switch(t) {
			case "integer":
				return Arg.DataType.INTEGER;
			case "float":
				return Arg.DataType.FLOAT;
			case "boolean":
				return Arg.DataType.BOOLEAN;
			default:
				return Arg.DataType.STRING;
		}
	}
	
	private class UserHandler extends DefaultHandler{
		Map<String, Boolean> flagMap;
		private Map<Integer, Arg> tempArgs;
		private String programDescription, programName, name, defaultVal, description;
		private char shortName;
		private String[] mutualExclusion;
		private List<String[]> mutexList;
		private Arg.DataType myType;
		private int position;
		private ArgsParser p;
		private final String[] XMLTags = 
			{"arguments", "programname", "programdescription", 
			"positional", "named", "name", 
			"type", "description", "shortname", 
			"default", "position", "restrictedvalues",
			"mutualexclusion"}; 
		private List<String> restrictedValues;
		
		public UserHandler(){
			p = new ArgsParser();
			flagMap = new HashMap<String, Boolean>();
			programDescription = "";
			programName = "";
			name = "";
			defaultVal = "";
			description = "";
			restrictedValues = new ArrayList<String>();
			mutexList = new ArrayList<String[]>();
			for(String s : XMLTags)
				flagMap.put(s, false);
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException {
			String currentTag = qName.toLowerCase();
			if(flagMap.containsKey(currentTag))
				flagMap.put(currentTag, true);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			String currentTag = qName.toLowerCase();
			
			if(currentTag.equals("named")) {
				if(shortName != '\u0000') {
					if(!restrictedValues.isEmpty())
						p.addNamedArg(name, myType, defaultVal, shortName, restrictedValues);
					else
						p.addNamedArg(name, myType, defaultVal, shortName);
				} else {
					if(!restrictedValues.isEmpty())
						p.addNamedArg(name, myType, defaultVal, restrictedValues);
					else
						p.addNamedArg(name, myType, defaultVal);
				}
			}
			else if(currentTag.equals("positional")) {
				if(restrictedValues.isEmpty()){
					p.addArg(name, myType, description);
				} else {
					p.addArg(name, myType, description, restrictedValues);
				}
			}
			else if(currentTag.equals("mutualexclusion")){
				mutexList.add(mutualExclusion);
				mutualExclusion = null;
			}
			 else if(currentTag.equals("arguments")){
				if(!mutexList.isEmpty()){
					for(String[] s : mutexList){
						p.addMutualExclusion(s);
					}
				}
			} 
			if(currentTag.equals("positional") || currentTag.equals("named")){
				restrictedValues = new ArrayList<String>();
				name = "";
				defaultVal = "";
				description = "";
				shortName = '\u0000';
			}
			flagMap.put(currentTag, false);
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException {
			String s = "";
			for(int i = start; i < start + length; i++) {
				s += String.valueOf(ch[i]);
			}
			
			if (flagMap.get("arguments")) {
				if(flagMap.get("programname")) {
					programName = s;
					p.setProgramName(programName);
				}
				else if(flagMap.get("programdescription")) {
					programDescription = s;
					p.setProgramDescription(programDescription);
				}
				else if(flagMap.get("mutualexclusion")){
					mutualExclusion = s.split(", ");
				}
				else if (flagMap.get("positional")) {
					if(flagMap.get("name")) {
						name = s;
					}
					else if(flagMap.get("type")) {
						myType = typeConversion(s);
					}
					else if(flagMap.get("description")) {
						description = s;
					}
					else if(flagMap.get("position")) {
						position = Integer.parseInt(s);
					}
					else if(flagMap.get("restrictedvalues")){
						restrictedValues = new ArrayList<String>(Arrays.asList(s.split(", ")));
					}
				}
				else if(flagMap.get("named")) {
					if(flagMap.get("name")) {
						name = s;
					}
					else if(flagMap.get("shortname")) {
						shortName = ch[start];
					}
					else if(flagMap.get("type")) {
						myType = typeConversion(s);
					}
					else if(flagMap.get("description")) {
						description = s;
					}
					else if(flagMap.get("default")) {
						defaultVal = s;
					}
					else if(flagMap.get("restrictedvalues")){
						restrictedValues = new ArrayList<String>(Arrays.asList(s.split(", ")));
					}
				}
			} 
		}
		
		public ArgsParser getArgsParser() {
			return p;
		}
	}
}
