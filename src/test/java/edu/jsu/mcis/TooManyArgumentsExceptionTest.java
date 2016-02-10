package edu.jsu.mcis;

import static org.junit.Assert.*;
import org.junit.*;

public class TooManyArgumentsExceptionTest{
	
	@Test
	public void testExtraArgumentsAssignedCorrectly() {
		ArgsParser p = new ArgsParser();
		String [] testArgs = {"4", "6", "14", "72"};
		String extraArgs = "initialvalue";
		p.addArg("one");
		p.addArg("two");
		p.addArg("three");
		try{
		p.parse(testArgs);
		}catch(TooManyArgumentsException t){
			extraArgs = t.getExtraArgs();
		}
		assertEquals("72",extraArgs);
	}
	
}