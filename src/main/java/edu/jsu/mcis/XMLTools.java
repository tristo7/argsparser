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

public class XMLTools{
	public static void save(ArgsParser p, String fileLocation){
		String xml = "<arguments>\n";
		if (!p.getProgramName().equals(""))
			xml += "    <programname>" + p.getProgramName() + "</programname>\n";
		if (!p.getProgramDescription().equals(""))
			xml += "    <programdescription>" + p.getProgramDescription() + "</programdescription>\n";
		int position = 1;
		for(String s : p.getPositionalArgumentNames()){
			String temp = p.getArg(s).toXML();
			temp = temp.substring(13);
			temp = "<position>" + String.valueOf(position) + "</position>\n" + temp;
			xml += "<positional>\n" + "    " + temp;
			position++;
		}
		for(String s : p.getOptionalArgumentNames()){
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
		private Arg.DataType myType;
		private int position;
		private ArgsParser p;
		private final String[] XMLTags = {"arguments", "programname", "programdescription", "positional", "named", "name", "type", "description", "shortname", "default", "position"}; 
		
		public UserHandler(){
			p = new ArgsParser();
			flagMap = new HashMap<String, Boolean>();
			programDescription = "";
			programName = "";
			name = "";
			defaultVal = "";
			description = "";
			for(String s : XMLTags){
				flagMap.put(s, false);
			}
		}
		
	   
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException {
			String currentTag = qName.toLowerCase();
			if(flagMap.containsKey(currentTag))
				flagMap.put(currentTag, true);
		}


	   @Override
		public void endElement(String uri, 
		String localName, String qName) throws SAXException {
			if(qName.equals("named")) {
				p.addOptionalArg(name, myType, defaultVal);
				if(shortName != '\u0000') {
					p.getArg(name).setArgShortName(shortName);
				}
				name = "";
				defaultVal = "";
				description = "";
			}
			else if(qName.equals("positional")) {
				p.addArg(name, myType, description);
				name = "";
				myType = Arg.DataType.STRING;
				description = "";
			}
			
			String currentTag = qName.toLowerCase();
			if(flagMap.get(currentTag))
				flagMap.put(currentTag, false);
		}

		@Override
		public void characters(char ch[], 
		  int start, int length) throws SAXException {
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
				}
			} 
		}
		
		public ArgsParser getArgsParser() {
			return p;
		}
	}
}
