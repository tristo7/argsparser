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
	
	@Test
	public void testHelpMessagePrintsWhenCalled() {
		String s = "-h";
		ArgsParser p = new ArgsParser();
		p.parse(s);
		assertEquals("usage: java VolumeCalculator length width height" + "\n"
							+ "Calculate the volume of a box." + "\n"
							+ "positional arguments:" + "\n"
							+ "\t" + "length the length of the box" + "\n"
							+ "\t" + "width the width of the box" + "\n"
							+ "\t" + "height the height of the box", outContent.toString());
	}
	
	/*
	@Test 
	public void testHelpMessagePrintsCorrectly() {
		
	}
	*/
}