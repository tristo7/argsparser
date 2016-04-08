import edu.jsu.mcis.*;

import java.util.*;
import java.lang.*;
public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		XMLTools x = new XMLTools();
		List<String> typeValues = new ArrayList<String>();
		typeValues.add("box");
		typeValues.add("cylinder");
		typeValues.add("sphere");
		
		p.setProgramName("VolumeCalculator");
		p.setProgramDescription("Calculate the volume of a cube, cylinder, or sphere.");
		p.addArg("length", Arg.DataType.FLOAT, "the length of the box (float)");
		p.addArg("width", Arg.DataType.FLOAT, "the width of the box (float)");
		p.addArg("height", Arg.DataType.FLOAT, "the height of the box (float)");
		p.addNamedArg("type", Arg.DataType.STRING, "box", 't', typeValues);
		p.addNamedArg("digits", Arg.DataType.INTEGER, "2", 'd');
		x.save(p, "testing.xml");
		p.parse(args);
		
		String option1 = p.getValue("type");
		int option2 = p.getValue("digits");
		double vol = 0;
		float h, l, w, r;
		h = l = w = r = 0;
		
		switch(option1) {
			case "box":
				l = p.getValue("length");
				h = p.getValue("height");
				w = p.getValue("width");
				vol = l*w*h;
				break;
			case "cylinder":
				h = p.getValue("height");
				r = p.getValue("width");
				vol = Math.PI*Math.pow(r, 2) * h;
				break;
			case "sphere":
				r = p.getValue("width");
				vol = (4.0 / 3.0)*Math.PI*Math.pow(r, 3);
				break;
		}
		
		System.out.println("Volume is "+ String.format("%." + Integer.toString(option2) + "f", vol) + ".");
		
	}
}