import edu.jsu.mcis.*;
import java.util.*;

public class AsciiArtGenerator{
	public static void main(String[] args) {
		List<String> shapeList = new ArrayList<String>();
		shapeList.add("triangle");
		shapeList.add("square");
		shapeList.add("diamond");
		ArgsParser p = new ArgsParser();
		p.setProgramName("AsciiArtGenerator");
		p.setProgramDescription("Generate ASCII art in shapes based upon arguments passed in.");
		p.addArg("shape", Arg.DataType.STRING, "the shape to be generated in ASCII.");
		p.setRestrictedValues("shape", shapeList);
		p.addArg("numberOfLines", Arg.DataType.INTEGER, "the number of Lines that the ASCII art will be generated on.");
		p.addNamedArg("pchar", Arg.DataType.STRING, "The placeholder character for the shape to be generated.", "*", 'p');
		p.parse(args);
		
		String option1 = p.getValue("pchar");
		String shape = p.getValue("shape");
		int numLines = p.getValue("numberOfLines");
		
		String generatedAscii = "";
		
		
		switch(shape) {
			case "triangle":
				generatedAscii = stringCreateTriangle(option1, numLines);
				break;
			case "square":
				generatedAscii = stringCreateSquare(option1, numLines);
				break;
			case "diamond":
				generatedAscii = stringCreateDiamond(option1, numLines);
				break;
			default:
				generatedAscii = stringCreateTriangle(option1, numLines);
				break;
		}
			
		System.out.print(generatedAscii);
	}
	private static String stringCreateTriangle(String pchar, int numLines) {
		int numChars = 1;
		String asciiTriangle = "";
		for(int i = 0; i < numLines; i++) {
			int numSpaces = numLines - i;
			for(int j = numSpaces; j > 0; j--) {
				asciiTriangle += " ";
			}
			for(int k = numChars; k > 0; k--) {
				asciiTriangle += pchar;
			}
			asciiTriangle += "\n";
			numChars += 2;
		}
		return asciiTriangle;
	}
	
	private static String stringCreateSquare(String pchar, int numLines) {
		String asciiSquare = "";
		int numChars = numLines;
		for(int i = 0; i < numLines; i++) {
			for(int j = 0; j < numChars; j++) {
				asciiSquare += pchar;
				asciiSquare += " ";
			}
			asciiSquare += "\n";
		}
		return asciiSquare;
	}
	
	private static String stringCreateDiamond(String pchar, int numLines) {
		if(numLines % 2 == 1) {
			String asciiDiamond = stringCreateTriangle(pchar, numLines/2 + 1);
			
			int numChars = 1;
			for(int i = 0; i < numLines/2 - 1; i++) {
				numChars += 2;
			}
			int numSpaces = 2;
			for(int m = numLines/2; m > 0; m--) {
				for(int j = 0; j < numSpaces; j++) {
					asciiDiamond += " ";
				}
				for(int k = 0; k < numChars; k++) {
					asciiDiamond += pchar;
				}
				asciiDiamond += "\n";
				numChars -= 2;
				numSpaces++;
			}
			return asciiDiamond;
		}
		else {
			return "Use an odd number of lines to create a diamond!";
		}
	}
}