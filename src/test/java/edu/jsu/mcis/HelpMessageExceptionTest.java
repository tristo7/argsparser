package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class HelpMessageExceptionTest{
	private ArgsParser p;
	
	@Before
	public void setup() {
		p = new ArgsParser();
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
}