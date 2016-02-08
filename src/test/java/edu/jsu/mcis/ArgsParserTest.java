package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class ArgsParserTest {
	private ArgsParser p;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
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
		String test = "arg";
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
	
	@Before
	public void setUpStream()
	{
		System.setOut(new PrintStream(outContent));
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
	
	@After
	public void cleanUpStream() {
		System.setOut(null);
	}
	
	@Test public void testHelpMessagePrintsCorrectly() {
		
	}
	
}