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
	
	@Test
	public void testHelpMessageFormattedCorrectly(){
		String[] testCommandLineArgs = {"-h"};
		String messageTest = "";
		String message = "usage: java VolumeCalculator length width height\n"+
					"Calcuate the volume of a box.\n"+
					"positional arguments:\n"+
					"length the length of the box (float)\n"+
					"width the width of the box(float)\n"+
					"height the height of the box(float)";
		try{
		p.parse(testCommandLineArgs);
		} catch(HelpMessageException h){
			messageTest = h.getMessage();
			}
		
		assertEquals(message,messageTest);
	}
	
	@Test
	public void testExtraArgumentsAssignedCorrectly() {
		ArgsParser p = new ArgsParser();
		String [] testArgs = {"4", "6", "14", "72", "43"};
		String extraArg = "initialvalue";
		String extraArgMessage = "initialvalue";
		
		p.addArg("one");
		p.addArg("two");
		p.addArg("three");
		try{
		p.parse(testArgs);
		}catch(TooManyArgumentsException t){
			extraArg = t.getExtraArgs();
			extraArgMessage = t.getMessage();
		}
		assertEquals("72 43",extraArg);
		assertEquals("usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: unrecognized arguments: 72 43", extraArgMessage);
	}
}