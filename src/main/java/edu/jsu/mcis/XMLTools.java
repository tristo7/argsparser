package edu.jsu.mcis;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.File;
import javax.xml.parse.*;

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
		try {
			if(fileLocation.contains(".xml")) {
				File xmlFile = new File(fileLocation);
				SAXParserFactory spFactory = SAXParserFactory.newInstance();
				SAXParser saxParse = spFactory.newSAXParser();
				DefaultHandler defaultH = new DefaultHandler();
				saxParse.parse(xmlFile, defaultH);
			}
			else {
				throw new Exception();
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
}

class UserHandler extends DefaultHandler {

   boolean isPositional = false;
   

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException {
      if (qName.equalsIgnoreCase("positional")) {
        isPositional = true;
      } 
	  else if(qName.equalsIgnoreCase("named"){
		isPostional = false;
	  }
	  else {
		throw new Exception();
	  }
   }


   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException {
      if (isPositional) {
         
      } 
	  else {
		
	  }
   }
}