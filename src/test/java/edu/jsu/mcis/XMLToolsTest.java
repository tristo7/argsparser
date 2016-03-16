package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class XMLToolsTest {
	private ArgsParser p;
	private XMLTools x;
	private ArgsParser q;
	
	@Before
	public void setUp() {
		p = new ArgsParser();
		x = new XMLTools();
		q = new ArgsParser();
		q = x.load("./src/test/java/edu/jsu/mcis/xmlFiles/testSave.xml");
	}
	
	@Test
	public void testSave(){
		p.setProgramName("Test");
		p.setProgramDescription("Test Program");
		p.addArg("one");
		p.addArg("two", Arg.DataType.INTEGER, "This is a test.");
		p.addOptionalArg("testArg", Arg.DataType.STRING, "test1", 't');
		p.addOptionalArg("testArg2", Arg.DataType.STRING, "test12");
		p.addOptionalArg("testArg3", Arg.DataType.STRING, "test123", 'c');
		p.getArg("testArg3").setDescription("NamedDescrip");
		x.save(p,"./build/tmp/testSave.xml");
		//read in xml file as string and test against known string
		String actualXMLOutput = "";
		String expectedXLMOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><arguments>    <programname>Test</programname>    <programdescription>Test Program</programdescription><positional>    <position>1</position>    <name>one</name>    <type>string</type></positional><positional>    <position>2</position>    <name>two</name>    <type>integer</type>    <description>This is a test.</description></positional>    <named>    <name>testArg</name>    <type>string</type>    <shortname>t</shortname>    <default>test1</default></named>    <named>    <name>testArg2</name>    <type>string</type>    <default>test12</default></named>    <named>    <name>testArg3</name>    <type>string</type>    <description>NamedDescrip</description>    <shortname>c</shortname>    <default>test123</default></named></arguments>";
		String currentLine = null;
		try{
			FileReader r = new FileReader("./build/tmp/testSave.xml");
			BufferedReader b = new BufferedReader(r);
			while((currentLine = b.readLine()) != null)
				actualXMLOutput += currentLine;
			b.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(expectedXLMOutput, actualXMLOutput);
		
	}

	@Test
	public void testLoadGetProgramName() {
		assertEquals("Test", q.getProgramName());
	}
	
	@Test
	public void testLoadGetProgramDescription() {
		assertEquals("Test Program", q.getProgramDescription());
	}
	
	@Test
	public void testGetArgName() {
		String s = q.getArg("one").getArgName();
		assertEquals("one", s);
	}
	
}