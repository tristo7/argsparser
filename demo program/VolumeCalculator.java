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
		p.setProgramDescription("Calculate the volume of a cube, cylinder, or sphere.\n" +
			"Order: \n  Box: Length, Width, Height\n"
			+ "Cylinder: Height Radius (IGNORED)\n"
			+ "Sphere: Radius (IGNORED) (IGNORED)");
		p.addArg("firstNum", Arg.DataType.FLOAT, "the length of the object (float)");
		p.addArg("secondNum", Arg.DataType.FLOAT, "the width of the object (float)");
		p.addArg("thirdNum", Arg.DataType.FLOAT, "the height of the object (float)");
		p.addNamedArg("type", Arg.DataType.STRING, "Shape of object: box, cylinder, or sphere.", "box", 't');
		p.setNamedArgToRequired("type");
		p.setRestrictedValues("type", typeValues);
		p.getArg("type").setDescription("Shape of object: box, cylinder, or sphere.");
		p.addNamedArg("digits", Arg.DataType.INTEGER, "decimal precision for answer.", "2", 'd');
		p.getArg("digits").setDescription("decimal precision for answer.");
		x.save(p, "testing.xml");
		p.parse(args);
		
		String option1 = p.getValue("type");
		int option2 = p.getValue("digits");
		double vol = 0;
		float h, l, w, r;
		h = l = w = r = 0;
		
		switch(option1) {
			case "box":
				l = p.getValue("firstNum");
				h = p.getValue("secondNum");
				w = p.getValue("thirdNum");
				vol = l*w*h;
				break;
			case "cylinder":
				h = p.getValue("firstNum");
				r = p.getValue("secondNum");
				vol = Math.PI*Math.pow(r, 2) * h;
				break;
			case "sphere":
				r = p.getValue("firstNum");
				vol = (4.0 / 3.0)*Math.PI*Math.pow(r, 3);
				break;
		}
		
		System.out.println("Volume is "+ String.format("%." + Integer.toString(option2) + "f", vol) + ".");
		
	}
}