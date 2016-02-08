
//Still need to find a way to properly run this from the command line.

import java.util.*;
import edu.jsu.mcis.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		
		//Convert the String array into String for parsing
		String s = "";
		for(int i = 0; i < args.length; i++) {
			s += args[i] + " ";
		}
	
		p.parse(s);
		
		float l = p.getArg("length");
		float h = p.getArg("height");
		float w = p.getArg("width");
		
		System.out.println("Volume is "+l*w*h+".");
	}
}