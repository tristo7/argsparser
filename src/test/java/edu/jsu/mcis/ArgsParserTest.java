package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

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
		p.addNamedArg("arg1", Arg.DataType.BOOLEAN, "False");
		assertFalse((boolean) p.getValue("arg1"));
		p.parse(new String[]{"--arg1", "blah"});
		assertTrue((boolean) p.getValue("arg1"));
		
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
		p.addNamedArg("arg1", Arg.DataType.BOOLEAN, "false");
		assertFalse((boolean) p.getValue("arg1"));
		boolean exception = false;
		String actualMessage = "initialvalue";
		try{
			p.addNamedArg("arg2", Arg.DataType.BOOLEAN, "true");
		} catch (FlagDefaultNotFalseException f){
			exception = true;
			actualMessage = f.getMessage();
			assertEquals("arg2",f.getName());
			assertEquals("true",f.getValue());
			
			
		}finally{
			assertTrue(exception);
			assertEquals("usage: java VolumeCalculator \n"+
					"VolumeCalculator.java: error: named argument arg2: invalid default value: true", actualMessage);
			
		}
		
	}
	
	@Test
	public void testNamedArgumentDefaultValueAndNumArguments(){
		p.addArg("l", Arg.DataType.FLOAT);
		p.addArg("w", Arg.DataType.FLOAT);
		p.addArg("h", Arg.DataType.FLOAT);
		p.addNamedArg("type", Arg.DataType.STRING, "cube");
		assertEquals("cube", p.getValue("type"));
		assertEquals(4,p.getNumArguments());	
	}
	
	@Test
	public void testNamedArgumentParsesCorrectly(){
		p.addArg("l", Arg.DataType.FLOAT);
		p.addArg("w", Arg.DataType.FLOAT);
		p.addArg("h", Arg.DataType.FLOAT);
		p.addNamedArg("type", Arg.DataType.STRING, "cube");
		String[] testCommandLineArgs = {"7","5","2", "--type", "pyramid"};
		p.parse(testCommandLineArgs);
		assertEquals("pyramid", p.getValue("type"));
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
		assertEquals(Float.valueOf(7), p.getValue("arg1"));
		assertEquals(Float.valueOf(5), p.getValue("arg2"));
		assertEquals(Float.valueOf(2), p.getValue("arg3"));
		assertEquals(Float.valueOf(4), p.getValue("arg4"));
	}
	
	@Test
	public void testIntArgumentIsParsedCorrectly() {
		String[] testCommandLineArgs = {"7"};
		p.addArg("arg1", Arg.DataType.INTEGER);
		p.parse(testCommandLineArgs);
		int argVal = p.getValue("arg1");
		assertEquals(1, p.getNumArguments());
		assertEquals(7, argVal);
	}
	
	@Test
	public void testBoolArgumentIsParsedCorrectly() {
		String[] testCommandLineArgs = {"True", "false"};
		p.addArg("arg1", Arg.DataType.BOOLEAN);
		p.addArg("arg2", Arg.DataType.BOOLEAN);
		p.parse(testCommandLineArgs);
		boolean argVal = p.getValue("arg1");
		boolean argVal2 = p.getValue("arg2");
		assertEquals(2, p.getNumArguments());
		assertEquals(true, argVal);
		assertEquals(false, argVal2);
	}
	
	@Test
	public void testStringArgumentIsParsedCorrectly() {
		String[] testCommandLineArgs = {"joe"};
		p.addArg("arg1", Arg.DataType.STRING);
		p.parse(testCommandLineArgs);
		String argVal = p.getValue("arg1");
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
	public void testInvalidNamedArgName() {
		p.addArg("one");
		p.addNamedArg("digits", Arg.DataType.STRING, "2");
		String incorrectArgName = "initialvalue";
		String incorrectArgMessage = "initialvalue";
		String[] testBadNamedArg = {"1", "--potato", "2"};
		
		try{
			p.parse(testBadNamedArg);
 		}catch(InvalidNamedArgumentNameException i) {
 			incorrectArgName = i.getName();
 			incorrectArgMessage = i.getMessage();
			
 		}finally{
			assertEquals("potato", incorrectArgName);
			assertEquals("usage: java VolumeCalculator one \nVolumeCalculator.java: error: named argument name: potato", incorrectArgMessage);
		}
 			

	}
	
	@Test
	public void testInvalidNamedArgValue() {
		p.addArg("one");
		p.addNamedArg("digits", Arg.DataType.INTEGER, "2");
		String argName = "initialvalue";
		String argDataType = "initialvalue";
		String actualMessage = "intialvalue";
		String expectedMessage = "usage: java VolumeCalculator one \n" +
                  "VolumeCalculator.java: error: named argument digits: invalid integer value: potato";
		String[] testBadNamedArg = {"1","--digits", "potato"};
		
		try{
			p.parse(testBadNamedArg);
		}catch(InvalidArgumentException i) {
			argName = i.getArgument().getName();
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
		p.addNamedArg("digits", Arg.DataType.INTEGER, "2", 'd');
		p.parse(new String[] {"-d", "4"});
		assertEquals((int) p.getValue("digits"), 4);
	}
	
	@Test
	public void testMultipleShortNameFlags(){
		p.addNamedArg("alpha", Arg.DataType.BOOLEAN, "false", 'a');
		p.addNamedArg("beta", Arg.DataType.BOOLEAN, "false", 'b');
		p.addNamedArg("charlie", Arg.DataType.BOOLEAN, "false", 'c');
		p.addNamedArg("delta", Arg.DataType.BOOLEAN, "false", 'd');
		
		
		p.parse(new String[] {"-cab"});
		assertTrue((boolean) p.getValue("alpha"));
		assertTrue((boolean) p.getValue("beta"));
		assertTrue((boolean) p.getValue("charlie"));
		assertFalse((boolean) p.getValue("delta"));
	}
	@Test
	public void testInvalidShortName(){
		p.addArg("one");
		p.addNamedArg("alpha", Arg.DataType.BOOLEAN, "false", 'a');
		String incorrectArgName = "";
		String incorrectArgMessage = "";
		try{
			p.parse(new String[] {"-b"});
 		}catch(InvalidNamedArgumentNameException i) {
 			incorrectArgName = i.getName();
 			incorrectArgMessage = i.getMessage();
			
 		}finally{
			assertEquals("b", incorrectArgName);
			assertEquals("usage: java VolumeCalculator one \nVolumeCalculator.java: error: named argument name: b", incorrectArgMessage);
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
	
	@Test
	public void testRestrictedValuesExceptionWithNormalArg(){
		boolean exceptionThrown = false;
		List<String> values = new ArrayList<String>();
		values.add("one");
		values.add("three");
		p.addArg("testArg", Arg.DataType.STRING, "This is a test.", values);
		try{
			p.parse(new String[] {"two"});
		} catch(RestrictedValuesException e){
			exceptionThrown = true;
		} finally{
			assertTrue(exceptionThrown);
		}
	}
	
	@Test
	public void testRestrictedValuesExceptionWithNamedArg(){
		boolean exceptionThrown = false;
		List<String> values = new ArrayList<String>();
		values.add("one");
		values.add("three");
		p.addArg("testArg");
		p.addNamedArg("testArg2", Arg.DataType.STRING, "one", 'a', values);
		Arg arg = new Arg("initialArgName");
		try{
			p.parse(new String[] {"one", "-a", "two"});
		} catch(RestrictedValuesException e){
			exceptionThrown = true;
			arg = e.getArgument();
		} finally{
			assertTrue(exceptionThrown);
			assertEquals("testArg2", arg.getName());
		}
	}
	
	@Test
	public void testRestrictedValues(){
		List<String> values = new ArrayList<String>();
		values.add("one");
		values.add("three");
		
		List<String> values2 = new ArrayList<String>();
		values2.add("1");
		values2.add("3");
		
		p.addArg("firstArg", Arg.DataType.STRING, "This is a test.", values);
		p.addNamedArg("secondArg", Arg.DataType.INTEGER, "1", 's', values2);
		
		p.parse(new String[] {"three", "-s", "3"} );
		
		assertEquals("three", (String) p.getValue("firstArg"));
		assertEquals(3, (int) p.getValue("secondArg"));
	}
	
	@Test
	public void testBooleanRestrictredValuesCausesException(){
		List<String> values = new ArrayList<String>();
		values.add("true");
		values.add("false");
		String s = "";
		try{
			p.addNamedArg("booleanArg", Arg.DataType.BOOLEAN, "false", 'b', values);
		} catch (RuntimeException e){
			s = e.getMessage();
		} finally{
			assertEquals("Boolean arguments do not need restricted values. They are either true of false.\n Argument name: booleanArg", s);
			try{
			p.addNamedArg("booleanArg2", Arg.DataType.BOOLEAN, "false", values);
			} catch (RuntimeException e){
				s = e.getMessage();
			} finally{
				assertEquals("Boolean arguments do not need restricted values. They are either true of false.\n Argument name: booleanArg2", s);
			}
			
		}
	}
	
	@Test
	public void testSetRestrictedValuesOnBooleanArgCausesException(){
		p.addNamedArg("boolean1", Arg.DataType.BOOLEAN, "false");
		List<String> values = new ArrayList<String>();
		values.add("whatever");
		String s = "";
		try{
			p.getArg("boolean1").setRestrictedValues(values);
		} catch (RuntimeException e){
			s = e.getMessage();
		} finally {
			assertEquals("Boolean arguments do not need restricted values. They are either true of false.\n Argument name: boolean1", s);
		}
	}
	
	@Test
	public void testGetRestrictedValuesCausesExceptionWhenNoneAreSet(){
		p.addArg("test");
		String s = "";
		try{
			p.getArg("test").getRestrictedValues();
		} catch (RuntimeException e){
			s = e.getMessage();
		} finally {
			assertEquals("test is not a restricted argument.", s);
		}
	}
	
	@Test
	public void testMutualExclusion(){
		p.addArg("test1");
		p.addNamedArg("one", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("two", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("three", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("four", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("five", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("six", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("seven", Arg.DataType.BOOLEAN, "false");
		p.addNamedArg("eight", Arg.DataType.BOOLEAN, "false");
		
		String[] exclusionOne = new String[] {"one", "two", "three"};
		String[] exclusionTwo = new String[] {"four", "five", "six"};
		String[] exclusionThree = new String[] {"three", "six"};
		
		p.addMutualExclusion(exclusionOne);
		p.addMutualExclusion(exclusionTwo);
		p.addMutualExclusion(exclusionThree);
		
		assertTrue(p.getMutualExclusion().contains(exclusionOne));
		assertTrue(p.getMutualExclusion().contains(exclusionTwo));
		assertTrue(p.getMutualExclusion().contains(exclusionThree));
		
		p.parse(new String []{"test1", "--one", "--four", "--seven"});
		
	}
	
	@Test
	public void testSettingRequiredArgument() {
		boolean exceptionThrown = false;
		p.addNamedArg("test1", Arg.DataType.STRING, "square");
		p.addNamedArg("test3", Arg.DataType.FLOAT, "20.7");
		p.addArg("test2", Arg.DataType.INTEGER, "This arg doesn't matter");
		p.setNamedArgToRequired("test1");
		p.setNamedArgToRequired("test3");
		
		String[] cla = {"19", "--test1", "sphere"};
		try {
			p.parse(cla);
		}catch(RuntimeException e) {
			exceptionThrown = true;
		}finally {
			assertTrue(exceptionThrown);
			assertEquals(p.getValue("test1"), "sphere");
		}
	}
	
	@Test
	public void testMutualExclusionException(){
		p.addArg("test1");
		p.addNamedArg("one", Arg.DataType.INTEGER, "1", 'o');
		p.addNamedArg("two", Arg.DataType.INTEGER, "2", 't');
		String[] exclusionOne = new String[] {"one", "two"};
		p.addMutualExclusion(exclusionOne);
		String expected, actual; 
		expected = "usage: java VolumeCalculator test1 \nVolumeCalculator.java: error: named argument two is in the mutually exclusive set [one, two].";
		actual = "";
		
		try{
			p.parse(new String[] {"testing", "-o", "2", "-t", "3"});
		} catch(MutualExclusionException e){
			actual = e.getMessage();
		} finally {
			assertEquals(expected, actual);
		}
		
	}
	
	@Test
	public void testAddMutualExclusionExceptionOnName(){
		String actual = "";
		try{
			p.addMutualExclusion(new String[]{"nothing", "everything"});
		} catch(InvalidNamedArgumentNameException e){
			actual = e.getMessage();
		} finally{
			assertEquals("usage: java VolumeCalculator \nVolumeCalculator.java: error: named argument name: nothing", actual);
		}
	}
	
	@Test
	public void testTooFewArgumentsExceptionTooFewArgsGiven() {
		boolean exceptionThrown = false;
		p.addNamedArg("test1", Arg.DataType.STRING, "square");
		p.addArg("test2", Arg.DataType.INTEGER, "This arg doesn't matter");
		String[] cla = {"--test1", "sphere"};
		try {
			p.parse(cla);
		}catch(TooFewArgumentsException t) {
			exceptionThrown = true;
		} finally {
			assertTrue(exceptionThrown);
		}
	}
	
	@Test
	public void testTooFewArgumentsExceptionRequiredArgsPassedIn() {
		boolean exceptionThrown = false;
		p.addNamedArg("test1", Arg.DataType.STRING, "square");
		p.addArg("test2", Arg.DataType.INTEGER, "This arg doesn't matter");
		p.setNamedArgToRequired("test1");
		String[] cla = {"19"};
		try {
			p.parse(cla);
		} catch(TooFewArgumentsException t) {
			exceptionThrown = true;
		} finally {
			assertTrue(exceptionThrown);
		}
	}
	
	@Test
	public void testTooFewArgumentsException() {
		boolean exceptionThrown = false;
		p.addNamedArg("test1", Arg.DataType.STRING, "square");
		p.addArg("test2", Arg.DataType.INTEGER, "This arg doesn't matter");
		p.addArg("test3", Arg.DataType.INTEGER, "This arg does matter");
		p.setNamedArgToRequired("test1");
		String[] cla = {"19"};
		try {
			p.parse(cla);
		} catch(TooFewArgumentsException t) {
			exceptionThrown = true;
		} finally {
			assertTrue(exceptionThrown);
		}
	}
}