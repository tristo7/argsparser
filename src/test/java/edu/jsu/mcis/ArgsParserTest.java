//package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgsParserTest {
	@Test
	public void testNewInstanceHasNoArguments() {
		ArgsParser p = new ArgsParser();
		assertEquals(0, p.getNumArguments());
	}
	
	@Test
	public void testArgumentIsAddedCorrectly() {
		ArgsParser p = new ArgsParser();
		
	}
	
	@Test
	public void testArgumentValueIsParsedCorrectly() {
		String[] s = {"17"};
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.parse(s);
		assertEquals("17", p.getArg("length"));
	}
	
	@Test
	public void testVolumeIsCalculatedCorrectly() {
		String[] s = {"7", "5", "2"};
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		p.parse(s);
		float length = Float.parseFloat(p.getArg("length"));
		float width = Float.parseFloat(p.getArg("width"));
		float height = Float.parseFloat(p.getArg("height"));
		float volume = length * width * height;
		assertEquals(70, volume);
	}
}