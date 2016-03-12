package edu.jsu.mcis;


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
		for(String s : p.getPositionalArgumentNames()){
			xml += "    " + p.getArg(s).toXML();
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
				UserHandler userH = new UserHandler();
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

	   
		private Arg.DataType myType;
		private String name;
		private ArgsParser a;
		private String programName;
		private String programDescription;
		private int position;
		private char shortName;
	   
	   @Override
	   public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException {
		  if (qName.equalsIgnoreCase("positional")) {
			isPositional = true;
		  } 
		  else if(qName.equalsIgnoreCase("named")){
			isPositional = false;
		  }
		  else {
			//throw new Exception();
		  }
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
					if(flagMap.get("programname") == true) {
						programName = new String(ch);
					}
					else if(flagMap.get("programdescription") == true) {
						programDescription = new String(ch);
					}
					else if (flagMap.get("positional") == true) {
						if(flagMap.get("name") == true) {
							name = new String(ch);
						}
						else if(flagMap.get("type") == true) {
							String s = new String(ch);
							myType = typeConversion(s);
						}
						else if(flagMap.get("position") == true) {
							
						}
					}
					else if(flagMap.get("named") == true) {
						
					}
				} 
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	   }
   
	}
}

