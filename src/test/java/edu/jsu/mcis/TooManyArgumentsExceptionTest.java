package edu.jsu.mcis;

import static org.junit.Assert.*;
import org.junit.*;

public class TooManyArgumentsExceptionTest{
	
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