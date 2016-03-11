package edu.jsu.mcis;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import javax.xml.parsers.*;

public class XMLTools{
	
	public static void save(ArgsParser p, String fileLocation){
		//convert string returned by Arg's toXML method to an XML doc.
		//use a loop on all existing arguments calling the method.
		//position is not functional as of yet.
		//to be moved to XMLTools.java
		
		//complete xml file as string
		String xml = 	"<arguments>\n";
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
			
			//NodeList nodes = doc.getElementsByTagName("")
			/* Element arguments = doc.createElement("arguments");
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
				position.appendChild(doc.createTextNode(Integer.toString(i+1)));
			}
			
			
			for(int i = 0; i<optionalArgNames.size();i++){
				currentArgName = optionalArgNames.get(i);
				Element named = doc.createElement("named");
				arguments.appendChild(named);
				
				Element name = doc.createElement("name");
				named.appendChild(name);
				name.appendChild(doc.createTextNode(currentArgName));
				
				if(shortNameMap.containsValue(currentArgName)){
					Element shortName = doc.createElement("shortname");
					named.appendChild(shortName);
					shortName.appendChild(doc.createTextNode(Character.toString(getArg(currentArgName).getArgShortName())));
				}
				
				Element type = doc.createElement("type");
				named.appendChild(type);
				type.appendChild(doc.createTextNode(argMap.get(currentArgName).getDataType()));
				
				Element defaultValue = doc.createElement("default");
				named.appendChild(defaultValue);
				String value = "";
				switch(argMap.get(currentArgName).getDataType()){
					case "string":
						value = argMap.get(currentArgName).getVal();
						break;
					case "boolean":
						value = Boolean.toString(argMap.get(currentArgName).getVal());
						break;
					case "float":
						value = Float.toString(argMap.get(currentArgName).getVal());
						break;
					case "integer":
						value = Integer.toString(argMap.get(currentArgName).getVal());
						break; 
				}
				defaultValue.appendChild(doc.createTextNode(value));
			} */
			
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
	
	public static ArgsParser load(String fileLocation){
		ArgsParser a = new ArgsParser();
		//parse the xml document with a loop that calls addArg & addOptionalArg accordingly.
		//return the resultant instance of ArgsParser.
		return a;
	}
}