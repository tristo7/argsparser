import edu.jsu.mcis.*;
import java.util.*;

public class Math {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.setProgramName("Math");
		p.setProgramDescription("Does simple math with three numbers.")
		p.addArg("first", Arg.DataType.FLOAT, "the first number");
		p.addArg("second", Arg.DataType.FLOAT, "the second number");
		p.addArg("third", Arg.DataType.FLOAT, "the third number");
		p.addOptionalArg("type", Arg.DataType.STRING, "add");
		p.parse(args);
		
		String typeOfMath = p.getArgValue("type");
		double result = 0;
		float f = 0;
		float s = 0;
		float t = 0;
		
		switch(typeOfMath) {
			case "add":
				f = p.getArgValue("first");
				s = p.getArgValue("second");
				t = p.getArgValue("third");
				result = (first + second + third);
				break;
			case "subtract":
				f = p.getArgValue("first");
				s = p.getArgValue("second");
				t = p.getArgValue("third");
				result = (first - second - third);
				break;
			case "multiply":
				f = p.getArgValue("first");
				s = p.getArgValue("second");
				t = p.getArgValue("third");
				result = (first * second * third);
				break;
		}
		
		System.out.println("The result is: " + result);
	}
}