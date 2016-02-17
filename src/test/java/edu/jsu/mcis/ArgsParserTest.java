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
	public void testFloatArgumentsParsedCorrectly() {
		String[] testCommandLineArgs = {"7","5","2","4"};
		p.addArg("arg1", Arg.DataType.FLOAT);
		p.addArg("arg2", Arg.DataType.FLOAT);
		p.addArg("arg3", Arg.DataType.FLOAT);
		p.addArg("arg4", Arg.DataType.FLOAT);
		p.parse(testCommandLineArgs);
		assertEquals(4, p.getNumArguments());
		assertEquals(Float.valueOf(7), p.getArgValue("arg1"));
		assertEquals(Float.valueOf(5), p.getArgValue("arg2"));
		assertEquals(Float.valueOf(2), p.getArgValue("arg3"));
		assertEquals(Float.valueOf(4), p.getArgValue("arg4"));
	}
	
	@Test
	public void testIntArgumentIsParsedCorrectly() {
		String[] testCommandLineArgs = {"7"};
		p.addArg("arg1", Arg.DataType.INTEGER);
		p.parse(testCommandLineArgs);
		int argVal = p.getArgValue("arg1");
		assertEquals(1, p.getNumArguments());
		assertEquals(7, argVal);
	}
	
	@Test
	public void testBoolArgumentIsParsedCorrectly() {
		String[] testCommandLineArgs = {"true"};
		p.addArg("arg1", Arg.DataType.BOOLEAN);
		p.parse(testCommandLineArgs);
		boolean argVal = p.getArgValue("arg1");
		assertEquals(1, p.getNumArguments());
		assertEquals(true, argVal);
	}
	
	@Test
	public void testStringArgumentIsParsedCorrectly() {
		String[] testCommandLineArgs = {"joe"};
		p.addArg("arg1", Arg.DataType.STRING);
		p.parse(testCommandLineArgs);
		String argVal = p.getArgValue("arg1");
		assertEquals(1, p.getNumArguments());
		assertEquals("joe", argVal);
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