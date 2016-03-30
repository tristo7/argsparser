package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

public class XMLToolsTest {
	private ArgsParser p;
	private XMLTools x;
	private ArgsParser q;
	private ArgsParser n;
	
	@Before
	public void setUp() {
		p = new ArgsParser(); // for save
		x = new XMLTools();
		q = new ArgsParser(); //for load xml file made from save
		q = x.load("./src/test/java/edu/jsu/mcis/xmlFiles/testSave.xml");
		n = new ArgsParser(); //for load xml file made NOT from save
		n = x.load("./src/test/java/edu/jsu/mcis/xmlFiles/testLoadFile.xml");
	}
	
	
	@Test
	public void testSave(){
		List<String> values = new ArrayList<String>();
		values.add("one");
		values.add("two");
		values.add("three");
		
		p.setProgramName("Test");
		p.setProgramDescription("Test Program");
		p.addArg("one");
		p.getArg("one").setRestrictedValues(values);
		p.addArg("two", Arg.DataType.INTEGER, "This is a test.");
		p.addOptionalArg("testArg", Arg.DataType.STRING, "test1", 't');
		p.addOptionalArg("testArg2", Arg.DataType.STRING, "test12");
		p.addOptionalArg("testArg3", Arg.DataType.STRING, "one", 'c', values);
		p.getArg("testArg3").setArgDescription("NamedDescrip");
		
		x.save(p,"./build/tmp/testSave.xml");
		//read in xml file as string and test against known string
		String actualXMLOutput = "";
		String expectedXLMOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><arguments>    <programname>Test</programname>    <programdescription>Test Program</programdescription><positional>    <position>1</position>    <name>one</name>    <type>string</type>    <restrictedvalues>one, two, three</restrictedvalues></positional><positional>    <position>2</position>    <name>two</name>    <type>integer</type>    <description>This is a test.</description></positional><named>    <name>testArg</name>    <type>string</type>    <shortname>t</shortname>    <default>test1</default></named><named>    <name>testArg2</name>    <type>string</type>    <default>test12</default></named><named>    <name>testArg3</name>    <type>string</type>    <restrictedvalues>one, two, three</restrictedvalues>    <description>NamedDescrip</description>    <shortname>c</shortname>    <default>one</default></named></arguments>";
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
	public void testLoadExceptionWithFileNotFound(){
		boolean exception = false;
		try{
			x.load("thisfiledoesnotexist.xml");
		} catch(XMLException e){
			exception = true;
		} finally{
			assertTrue(exception);
		}
	}
	
	@Test
	public void testLoadExceptionWithFileNotXMLExtension(){
		boolean exception = false;
		try{
			x.load("thisfiledoesnotexist");
		} catch(XMLException e){
			exception = true;
		} finally{
			assertTrue(exception);
		}
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
	
	@Test
	public void testGetArgDataType() {
		String s = q.getArg("one").getDataType();
		assertEquals("string", s);
	}
	
	@Test
	public void testGetRestrictedValues(){
		assertEquals("[one, two, three]", q.getArg("one").getRestrictedValues());
		assertEquals("[one, two, three]", q.getArg("testArg3").getRestrictedValues());
	}
	
	@Test
	public void testLoadForAnotherFile() {

		String s = n.getArg("square").getDataType();
		String m = n.getArg("blue").getArgName();
		assertEquals("TestLoad", n.getProgramName());
		assertEquals("TestLoadProgram", n.getProgramDescription());
		assertEquals("integer", s);
		assertEquals("blue", m);
	}
	
}