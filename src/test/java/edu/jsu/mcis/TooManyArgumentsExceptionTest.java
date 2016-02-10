package edu.jsu.mcis.*;

import static org.junit.Assert.*;

public class TooManyArgumentsExceptionTest() {
	
	@Test
	public void testReturnsExtraArguments() {
		ArgsParser p = new ArgsParser();
		String [] testArgs = {4, 6, 14, 72};
		
		p.addArg("one");
		p.addArg("two");
		p.addArg("three");
		p.parse(testArgs);
		
		
		
		assertEquals():
	}
	
	
}