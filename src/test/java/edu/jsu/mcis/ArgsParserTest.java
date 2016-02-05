package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgsParserTest {
	@Before
	public void SetUp() {
		
	}
	
	@Test
	public void testNewInstanceHasNoArguments() {
		ArgsParser p = new ArgsParser();
		assertEquals(0, p.getNumArguments());
	}
	
	@Test
	public void testArgumentIsAddedCorrectly() {
		String test = "arg"
		ArgsParser p = new ArgsParser();
		p.addArg(test);
		assertEquals(1, p.getNumArguments());
		assertEquals("arg", p.getArg("arg"));
	}
	
	@Test
	public void testArgumentValueIsParsedCorrectly() {
		String s = "17";
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.parse(s);
		assertEquals("17", p.getArg("length"));
	}
	
}