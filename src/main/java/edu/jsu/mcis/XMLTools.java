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
	private static UserHandler userH = new UserHandler();
	
	public static void save(ArgsParser p, String fileLocation){
		String xml = "<arguments>\n";
		if (!p.getProgramName().equals(""))
			xml += "    <programname>" + p.getProgramName() + "</programname>\n";
		if (!p.getProgramDescription().equals(""))
			xml += "    <programdescription>" + p.getProgramDescription() + "</programdescription>\n";
		int position = 1;
		for(String s : p.getPositionalArgumentNames()){
			String temp = p.getArg(s).toXML();
			temp = temp.substring(0,58);
			temp += "    <position>" + String.valueOf(position) + "</position>\n</positional>";
			xml += "    " + temp;
			position++;
		}
		for(String s : p.getOptionalArgumentNames()){
			xml += "    " + p.getArg(s).toXML();
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
			e.printStackTrace();
		}
		
	}
	
	public static ArgsParser load(String fileLocation){
		ArgsParser a = new ArgsParser();
		//parse the xml document with a loop that calls addArg & addOptionalArg accordingly.
		//return the resultant instance of ArgsParser.
		try {
			if(fileLocation.contains(".xml")) {
				File xmlFile = new File(fileLocation);
				SAXParserFactory spFactory = SAXParserFactory.newInstance();
				SAXParser saxParse = spFactory.newSAXParser();
				saxParse.parse(xmlFile, userH);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return a;
	}
	
	private Arg.DataType typeConversion(String t) {
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
	
	private class UserHandler extends DefaultHandler {
		private Map<String, Boolean> flagMap;
		boolean isPositional = false;
		private List<Arg> tempArgs;
		private String programDescription;
		private String programName;
		private String name;
		private String defaultVal;
		private char shortName;
		private Arg.DataType myType;
		private ArgsParser p;
		
		public UserHandler(){
			p = new ArgsParser();
			flagMap = new HashMap<String, Boolean>();
			flagMap.put("arguments", false);
			flagMap.put("programname", false);
			flagMap.put("programdescription", false);
			flagMap.put("positional", false);
			flagMap.put("named", false);
			flagMap.put("name", false);
			flagMap.put("type", false);
			flagMap.put("description", false);
			flagMap.put("shortname", false);
			flagMap.put("default", false);
			flagMap.put("position", false);
		}
	   
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException {
			String currentTag = qName.toLowerCase();
			if(flagMap.constainsKey(currentTag))
				flagMap.put(currentTag, true);
		}


	   @Override
		public void endElement(String uri, 
		String localName, String qName) throws SAXException {
			if (qName.equalsIgnoreCase("student")) {
				System.out.println("End Element :" + qName);
			}
		}

		@Override
		public void characters(char ch[], 
		  int start, int length) throws SAXException {
			try {
				if (flagMap.get("arguments") == true) {
					Arg tempArg;
					if(flagMap.get("programname") == true) {
						programName = new String(ch);
						p.setProgramName(programName);
					}
					else if(flagMap.get("programdescription") == true) {
						programDescription = new String(ch);
						p.setProgramDescription(programDescription);
					}
					else if (flagMap.get("positional") == true) {
						if(flagMap.get("name") == true) {
							name = new String(ch);
						}
						else if(flagMap.get("type") == true) {
							String s = new String(ch);
							myType = typeConversion(s);
							tempArg = new Arg(name, myType, "");
						}
						else if(flagMap.get("position") == true) {
							int myPosition = Integer.parseInt(new String(ch));
							tempArg.setPosition(myPosition);
							tempArgs.add(tempArg);
						}
					}
					else if(flagMap.get("named") == true) {
						if(flagMap.get("name") == true) {
							name = new String(ch);
						}
						else if(flagMap.get("shortname") == true) {
							shortName = ch[0];
						}
						else if(flagMap.get("type") == true) {
							String p = new String(ch);
							myType = typeConversion(p);
						}
						else if(flagMap.get("default") == true) {
							
						}
					}
				} 
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	   }
	}
}

