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
	public XMLTools (){
		
	}
	
	public static void save(ArgsParser p, String fileLocation){
		String xml = "<arguments>\n";
		if (!p.getProgramName().equals(""))
			xml += "    <programname>" + p.getProgramName() + "</programname>\n";
		if (!p.getProgramDescription().equals(""))
			xml += "    <programdescription>" + p.getProgramDescription() + "</programdescription>\n";
		int position = 1;
		for(String s : p.getPositionalArgumentNames()){
			String temp = p.getArg(s).toXML();
			System.out.println(temp);
			temp = temp.substring(13);
			System.out.println(temp);
			temp = "<position>" + String.valueOf(position) + "</position>\n" + temp;
			xml += "<positional>\n" + "    " + temp;
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

	private static class UserHandler extends DefaultHandler{
		Map<String, Boolean> flagMap;
		boolean isPositional = false;
		private List<Arg> tempArgs;
		private String programDescription;
		private String programName;
		private String name;
		private String defaultVal;
		private char shortName;
		private Arg.DataType myType;
		private ArgsParser p;
		private final String[] XMLTags = {"arguments", "programname", "programdescription", "positional", "named", "name", "type", "description", "shortname", "default", "position"}; 
		
		public UserHandler(){
			p = new ArgsParser();
			flagMap = new HashMap<String, Boolean>();
			tempArgs = new ArrayList<Arg>();
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
			for(Arg a : tempArgs) {
				p.addArg(a);
			}
			String currentTag = qName.toLowerCase();
			if(flagMap.get(currentTag))
				flagMap.put(currentTag, false);
		}

		@Override
		public void characters(char ch[], 
		  int start, int length) throws SAXException {
			try {
				if (flagMap.get("arguments")) {
					Arg tempArg;
					if(flagMap.get("programname")) {
						programName = new String(ch);
						p.setProgramName(programName);
					}
					else if(flagMap.get("programdescription")) {
						programDescription = new String(ch);
						p.setProgramDescription(programDescription);
					}
					else if (flagMap.get("positional")) {
						if(flagMap.get("name")) {
							name = new String(ch);
						}
						else if(flagMap.get("type")) {
							String s = new String(ch);
							myType = typeConversion(s);
						}
						else if(flagMap.get("position")) {
							int argPos = Integer.parseInt(new String(ch));
							tempArg = new Arg(name, myType, "");
							//tempArg.setPosition(argPos);
							tempArgs.add(tempArg);
						}
					}
					else if(flagMap.get("named")) {
						if(flagMap.get("name")) {
							name = new String(ch);
						}
						else if(flagMap.get("shortname")) {
							shortName = ch[0];
						}
						else if(flagMap.get("type")) {
							String s = new String(ch);
							myType = typeConversion(s);
						}
						else if(flagMap.get("default")) {
							String d = new String(ch);
							tempArg = new Arg(name, myType, "", d);
							tempArg.setArgShortName(shortName);
							tempArgs.add(tempArg);
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
