package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class ArgTest {
	private ArgsParser p;
	
	@Before
	public void setUp() {
		p = new ArgsParser();
	}
	
	@Test
	public void testDescription(){
		p.addArg("arg1", Arg.DataType.FLOAT, "This is an argument. (float)");
		assertEquals("This is an argument. (float)", p.getArg("arg1").getDescription());
		p.getArg("arg1").setDescription("This is a test.");
		assertEquals("This is a test.", p.getArg("arg1").getDescription());
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
}