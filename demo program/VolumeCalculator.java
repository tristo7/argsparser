import edu.jsu.mcis.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.setProgramName("VolumeCalculator");
		p.setProgramDescription("Calculate the volume of a cube, cylinder, or sphere.");
		p.addArg("length", Arg.DataType.FLOAT, "the length of the box (float)");
		p.addArg("width", Arg.DataType.FLOAT, "the width of the box(float)");
		p.addArg("height", Arg.DataType.FLOAT, "the height of the box(float)");
		p.addOptionalArg("type", Arg.DataType.STRING, "box");
		p.addOptionalArg("digits", Arg.DataType.INTEGER, "2", 'd');
		//p.saveToXML("test.xml");
		System.out.println(p.getArg("length").toXML());
		System.out.println(p.getArg("width").toXML());
		System.out.println(p.getArg("type").toXML());
		System.out.println(p.getArg("digits").toXML());
		p.parse(args);
		
		String option1 = p.getArgValue("type");
		int option2 = p.getArgValue("digits");
		double vol = 0;
		float h = 0;
		float l = 0;
		float w = 0;
		float r = 0;
		
		switch(option1) {
			case "box":
				l = p.getArgValue("length");
				h = p.getArgValue("height");
				w = p.getArgValue("width");
				vol = l*w*h;
				break;
			case "cylinder":
				h = p.getArgValue("height");
				r = p.getArgValue("width");
				vol = Math.PI*Math.pow(r, 2) * h;
				break;
			case "sphere":
				r = p.getArgValue("width");
				vol = (4.0 / 3.0)*Math.PI*Math.pow(r, 3);
				break;
			default:
				System.err.println("Invalid object type.");
				System.exit(1);
				break;
		}
		
		System.out.println("Volume is "+ String.format("%." + Integer.toString(option2) + "f", vol) + ".");
		
	}
}