package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

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
	public void testArgumentIsAddedCorrectly() {
		String testString = "arg";
		int testVal = 7;
		p.addArgName(test);
		p.parse()
		assertEquals(1, p.getNumArguments());
		assertEquals(testVal, p.getArg("arg"));
	}
	
	@Test
	public void testArgumentValueIsParsedCorrectly() {
		String s = "17";
		p.addArg("length");
		p.parse(s);
		assertEquals("17", p.getArg("length"));
	}
	
}