package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class ArgsParserTest {
	private ArgsParser p;
	
	@Before
	public void setup() {
		p = new ArgsParser();
	}
	
	@Test
	public void testNewInstanceHasNoArguments() {
		assertEquals(0, p.getNumArguments());
	}
	
	@Test
	public void testArgumentsParsedCorrectly() {
		String[] testCommandLineArgs = {"7","5","2","4"};
		p.addArg("arg1");
		p.addArg("arg2");
		p.addArg("arg3");
		p.addArg("arg4");
		p.parse(testCommandLineArgs);
		assertEquals(4, p.getNumArguments());
		assertEquals("7", p.getArg("arg1"));
		assertEquals("5", p.getArg("arg2"));
		assertEquals("2", p.getArg("arg3"));
		assertEquals("4", p.getArg("arg4"));
	}
	
	
	@Test
	public void testCorrectNumOfArgumentsCalculated(){
		p.addArg("test1");
		p.addArg("test2");
		assertEquals(2,p.getNumArguments());
	}
}