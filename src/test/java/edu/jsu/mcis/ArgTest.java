package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgTest{
	private Arg argument;
	
	@Before
	public void setup(){
		argument = new Arg();
	}
	
	@Test
	public void testSetName(){
		argument.setName("This is a test.");
		assertEquals("This is a test.",argument.getName());
	}
	
	@Test
	public void testSetValue(){
		argument.setValue("testvalue");
		assertEquals("testvalue",argument.getValue());
	}
	
}