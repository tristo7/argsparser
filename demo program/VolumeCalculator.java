
//Still need to find a way to properly run this from the command line.

import java.util.*;
import edu.jsu.mcis.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		
		p.parse(args);
		
		float l = Integer.parseInt(p.getArg("length"));
		float h = Integer.parseInt(p.getArg("height"));
		float w = Integer.parseInt(p.getArg("width"));
		
		System.out.println("Volume is "+Math.round(l*w*h)+".");
	}
}