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
	}
	
	
}