package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

public class ArgTest {
	private ArgsParser p;
	private XMLTools x;
	
	@Before
	public void setUp() {
		p = new ArgsParser();
	}
	
	@Test
	public void testToXML(){
		p.addArg("one");
		assertEquals("<positional>\n    <name>one</name>\n    <type>string</type>\n</positional>\n",
			p.getArg("one").toXML());
		
		p.addArg("two", Arg.DataType.INTEGER, "This is a test.");
		assertEquals("<positional>\n    <name>two</name>\n    <type>integer</type>\n    <description>This is a test.</description>\n</positional>\n",
			p.getArg("two").toXML());
		
		p.addOptionalArg("testArg", Arg.DataType.STRING, "test1", 't');
		assertEquals("<named>\n    <name>testArg</name>\n    <type>string</type>\n    <shortname>t</shortname>\n    <default>test1</default>\n</named>\n",
			p.getArg("testArg").toXML());
		
		p.addOptionalArg("testArg2", Arg.DataType.STRING, "test12");
		assertEquals("<named>\n    <name>testArg2</name>\n    <type>string</type>\n    <default>test12</default>\n</named>\n",
			p.getArg("testArg2").toXML());
		
		p.addOptionalArg("testArg3", Arg.DataType.STRING, "test123", 'c');
		p.getArg("testArg3").setArgDescription("NamedDescrip");
		assertEquals("<named>\n    <name>testArg3</name>\n    <type>string</type>\n    <description>NamedDescrip</description>\n    <shortname>c</shortname>\n    <default>test123</default>\n</named>\n",
			p.getArg("testArg3").toXML());
		
		List<String> values = new ArrayList<String>();
		values.add("1");
		values.add("3");
		values.add("5");
		values.add("7");
		
		p.addOptionalArg("testArg4", Arg.DataType.INTEGER, "1", 'r', values);
		assertEquals("<named>\n    <name>testArg4</name>\n    <type>integer</type>\n    <restrictedvalues>1, 3, 5, 7</restrictedvalues>\n    <shortname>r</shortname>\n    <default>1</default>\n</named>\n",
			p.getArg("testArg4").toXML());
	}
	
	@Test
	public void testGetArgShortName(){
		p.addOptionalArg("testArg", Arg.DataType.STRING, "test", 't');
		assertEquals('t', p.getArg("testArg").getArgShortName());
	}
	
	@Test
	public void testDescription(){
		p.addArg("arg1", Arg.DataType.FLOAT, "This is an argument. (float)");
		assertEquals("This is an argument. (float)", p.getArg("arg1").getArgDescription());
		p.getArg("arg1").setArgDescription("This is a test.");
		assertEquals("This is a test.", p.getArg("arg1").getArgDescription());
	}
	
	@Test
	public void testArgValueAndDataTypeIsStoredCorrectly() {
		String[] testCommandLineArgs = {"7"};
		p.addArg("arg1", Arg.DataType.INTEGER);
		p.parse(testCommandLineArgs);
		int argVal = p.getArgValue("arg1");
		assertEquals(7, argVal);
	}
	
	@Test
	public void testArgValueDefaultIsString() {
		String[] testCommandLineArgs = {"bob"};
		p.addArg("arg1");
		p.parse(testCommandLineArgs);
		String argVal = p.getArgValue("arg1");
		assertEquals("bob", argVal);
		assertEquals("string", p.getArg("arg1").getDataType());
	}
	
	@Test
	public void testGetShortNameException(){
		p.addOptionalArg("test", Arg.DataType.STRING, "default");
		p.addArg("testing");
		try{
		p.getArg("test").getArgShortName();
		} catch(InvalidArgumentException e){
			assertEquals("test does not have a shortname.", e.getMessage());
		}
		
		try{
			p.getArg("testing").getArgShortName();
		}catch(InvalidArgumentException e){
			assertEquals("testing is not a named argument.", e.getMessage());
		}
	}
	
	@Test
	public void testSetShortNameException(){
		p.addArg("test");
		try{
		p.getArg("test").setArgShortName('t');
		} catch(InvalidArgumentException e){
			assertEquals("test is a positional argument.", e.getMessage());
		}
	}
}