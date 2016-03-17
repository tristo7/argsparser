package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class ArgsParserTest {
	private ArgsParser p;
	
	@Before
	public void setup() {
		p = new ArgsParser();
		p.setProgramName("VolumeCalculator");
		p.setProgramDescription("Calcuate the volume of a box.");
	}
	
	@Test
	public void testNewInstanceHasNoArguments() {
		assertEquals(0, p.getNumArguments());
	}
	
	@Test
	public void testFlagArgumentFromCLA(){
		p.addArg("TestArg");
		p.addOptionalArg("arg1", Arg.DataType.BOOLEAN, "False");
		assertFalse((boolean) p.getArgValue("arg1"));
		p.parse(new String[]{"--arg1"});
		assertTrue((boolean) p.getArgValue("arg1"));
		
		String messageTest = "";
		String message = "usage: java VolumeCalculator TestArg \n"+
					"VolumeCalculator.java: Calcuate the volume of a box.\n"+
					"positional arguments:\n"+
					"TestArg \n";
		try{
		p.parse(new String[] {"--help"});
		} catch(HelpMessageException h){
			messageTest = h.getMessage();
		}finally{
			assertEquals(message,messageTest);
		}
	}
	
	@Test
	public void testFlagArgumentDefaults(){
		p.addOptionalArg("arg1", Arg.DataType.BOOLEAN, "false");
		assertFalse((boolean) p.getArgValue("arg1"));
		boolean exception = false;
		String actualMessage = "initialvalue";
		try{
			p.addOptionalArg("arg2", Arg.DataType.BOOLEAN, "true");
		} catch (FlagDefaultNotFalseException f){
			exception = true;
			actualMessage = f.getMessage();
			assertEquals("arg2",f.getArgName());
			assertEquals("true",f.getArgValue());
			
			
		}finally{
			assertTrue(exception);
			assertEquals("usage: java VolumeCalculator \n"+
					"VolumeCalculator.java: error: optional argument arg2: invalid default value: true", actualMessage);
			
		}
		
	}
	
	@Test
	public void testOptionalArgumentDefaultValueAndNumArguments(){
		p.addArg("l", Arg.DataType.FLOAT);
		p.addArg("w", Arg.DataType.FLOAT);
		p.addArg("h", Arg.DataType.FLOAT);
		p.addOptionalArg("type", Arg.DataType.STRING, "cube");
		assertEquals("cube", p.getArgValue("type"));
		assertEquals(4,p.getNumArguments());	
	}
	
	@Test
	public void testOptionalArgumentParsesCorrectly(){
		p.addArg("l", Arg.DataType.FLOAT);
		p.addArg("w", Arg.DataType.FLOAT);
		p.addArg("h", Arg.DataType.FLOAT);
		p.addOptionalArg("type", Arg.DataType.STRING, "cube");
		String[] testCommandLineArgs = {"7","5","2", "--type", "pyramid"};
		p.parse(testCommandLineArgs);
		assertEquals("pyramid", p.getArgValue("type"));
		assertEquals(4,p.getNumArguments());
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
		String[] testCommandLineArgs = {"True", "false"};
		p.addArg("arg1", Arg.DataType.BOOLEAN);
		p.addArg("arg2", Arg.DataType.BOOLEAN);
		p.parse(testCommandLineArgs);
		boolean argVal = p.getArgValue("arg1");
		boolean argVal2 = p.getArgValue("arg2");
		assertEquals(2, p.getNumArguments());
		assertEquals(true, argVal);
		assertEquals(false, argVal2);
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
	public void testInvalidArgumentExceptionWithBoolean(){
		
		p.addArg("a", Arg.DataType.BOOLEAN);
		p.addArg("b", Arg.DataType.BOOLEAN);
		String[] testCommandLineArgs = {"true","randomtext"};
		String message = "usage: java VolumeCalculator a b \n" +
                  "VolumeCalculator.java: error: argument b: invalid boolean value: randomtext";
		String messageTest = "initialvalue";
		try{
			p.parse(testCommandLineArgs);
		} catch (InvalidArgumentException i){
			messageTest = i.getMessage();
		}finally{
			assertEquals(message,messageTest);
		}

	}
	
	@Test
	public void testInvalidArgumentExceptionWithFloat(){
		p.addArg("a", Arg.DataType.FLOAT);
		p.addArg("b", Arg.DataType.FLOAT);
		String[] testCommandLineArgs = {"5.5","randomtext"};
		String message = "usage: java VolumeCalculator a b \n" +
                  "VolumeCalculator.java: error: argument b: invalid float value: randomtext";
		String messageTest = "initialvalue";
		try{
			p.parse(testCommandLineArgs);
		} catch (InvalidArgumentException i){
			messageTest = i.getMessage();
		}finally{
			assertEquals(message,messageTest);
		}

	}
	
		
	
	@Test
	public void testInvalidArgumentExceptionWithInteger(){
		p.addArg("a", Arg.DataType.INTEGER);
		p.addArg("b", Arg.DataType.INTEGER);
		String[] testCommandLineArgs = {"5","randomtext"};
		String message = "usage: java VolumeCalculator a b \n" +
                  "VolumeCalculator.java: error: argument b: invalid integer value: randomtext";
		String messageTest = "initialvalue";
		try{
			p.parse(testCommandLineArgs);
		} catch (InvalidArgumentException i){
			messageTest = i.getMessage();
		}finally{
			assertEquals(message,messageTest);
		}
	}
	
	@Test
	public void testHelpMessageExceptionFormattedCorrectly(){
		p.addArg("length", "the length of the box (float)");
		p.addArg("width", "the width of the box(float)");
		p.addArg("height", "the height of the box(float)");
		
		String[] testCommandLineArgs = {"-h"};
		String messageTest = "";
		String message = "usage: java VolumeCalculator length width height \n"+
					"VolumeCalculator.java: Calcuate the volume of a box.\n"+
					"positional arguments:\n"+
					"length the length of the box (float)\n"+
					"width the width of the box(float)\n"+
					"height the height of the box(float)\n";
		try{
		p.parse(testCommandLineArgs);
		} catch(HelpMessageException h){
			messageTest = h.getMessage();
		}finally{
			assertEquals(message,messageTest);
		}
	}
	
	@Test
	public void testTooManyArgumentsException() {
		String[] testCommandLineArgs = {"4", "6", "14", "72", "43"};
		String extraArgs = "initialvalue";
		String extraArgMessage = "initialvalue";
		
		p.addArg("one");
		p.addArg("two");
		p.addArg("three");
		try{
		p.parse(testCommandLineArgs);
		}catch(TooManyArgumentsException t){
			extraArgs = t.getExtraArgs();
			extraArgMessage = t.getMessage();
		}finally{
			assertEquals("72 43",extraArgs);
			assertEquals("usage: java VolumeCalculator one two three \nVolumeCalculator.java: error: unrecognized arguments: 72 43", extraArgMessage);
		}

	}
	
	@Test
	public void VolumeCalculatorNameAndDescription() {
		assertEquals("VolumeCalculator", p.getProgramName());
		assertEquals("Calcuate the volume of a box.", p.getProgramDescription());
	}
	
	@Test
	public void testInvalidOptionalArgName() {
		p.addArg("one");
		p.addOptionalArg("digits", Arg.DataType.STRING, "2");
		String incorrectArgName = "initialvalue";
		String incorrectArgMessage = "initialvalue";
		String[] testBadOptionalArg = {"1", "--potato", "2"};
		
		try{
			p.parse(testBadOptionalArg);
 		}catch(InvalidOptionalArgumentNameException i) {
 			incorrectArgName = i.getArgName();
 			incorrectArgMessage = i.getMessage();
			
 		}finally{
			assertEquals("potato", incorrectArgName);
			assertEquals("usage: java VolumeCalculator one \nVolumeCalculator.java: error: optional argument name: potato", incorrectArgMessage);
		}
 			

	}
	
	@Test
	public void testInvalidOptionalArgValue() {
		p.addArg("one");
		p.addOptionalArg("digits", Arg.DataType.INTEGER, "2");
		String argName = "initialvalue";
		String argDataType = "initialvalue";
		String actualMessage = "intialvalue";
		String expectedMessage = "usage: java VolumeCalculator one \n" +
                  "VolumeCalculator.java: error: optional argument digits: invalid integer value: potato";
		String[] testBadOptionalArg = {"1","--digits", "potato"};
		
		try{
			p.parse(testBadOptionalArg);
		}catch(InvalidArgumentException i) {
			argName = i.getArgument().getArgName();
			argDataType = i.getArgument().getDataType();
			actualMessage = i.getMessage();
		}finally{
			assertEquals("digits",argName);
			assertEquals(expectedMessage,actualMessage);
			assertEquals("integer",argDataType);
		}
	}
	
	@Test
	public void testShortNameMapsToLongName(){
		p.addOptionalArg("digits", Arg.DataType.INTEGER, "2", 'd');
		p.parse(new String[] {"-d", "4"});
		assertEquals((int) p.getArgValue("digits"), 4);
	}
	
	@Test
	public void testMultipleShortNameFlags(){
		p.addOptionalArg("alpha", Arg.DataType.BOOLEAN, "false", 'a');
		p.addOptionalArg("beta", Arg.DataType.BOOLEAN, "false", 'b');
		p.addOptionalArg("charlie", Arg.DataType.BOOLEAN, "false", 'c');
		p.addOptionalArg("delta", Arg.DataType.BOOLEAN, "false", 'd');
		
		
		p.parse(new String[] {"-cab"});
		assertTrue((boolean) p.getArgValue("alpha"));
		assertTrue((boolean) p.getArgValue("beta"));
		assertTrue((boolean) p.getArgValue("charlie"));
		assertFalse((boolean) p.getArgValue("delta"));
	}
	@Test
	public void testInvalidShortName(){
		p.addArg("one");
		p.addOptionalArg("alpha", Arg.DataType.BOOLEAN, "false", 'a');
		String incorrectArgName = "";
		String incorrectArgMessage = "";
		try{
			p.parse(new String[] {"-b"});
 		}catch(InvalidOptionalArgumentNameException i) {
 			incorrectArgName = i.getArgName();
 			incorrectArgMessage = i.getMessage();
			
 		}finally{
			assertEquals("b", incorrectArgName);
			assertEquals("usage: java VolumeCalculator one \nVolumeCalculator.java: error: optional argument name: b", incorrectArgMessage);
		}
	}
	
	@Test
	public void testGetArgException(){
		String msg = "default";
		try{
			p.getArg("thisdoesnotexist");
		}catch(RuntimeException e){
			msg = e.getMessage();
		} finally{
			assertEquals("Argument thisdoesnotexist does not exist.", msg);
		}
	}

}