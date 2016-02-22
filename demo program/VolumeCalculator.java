import edu.jsu.mcis.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.addArg("length", Arg.DataType.FLOAT);
		p.addArg("width", Arg.DataType.FLOAT);
		p.addArg("height", Arg.DataType.FLOAT);
		p.addOptionalArg("type", Arg.DataType.STRING, "cube");
		p.addOptionalArg("digits", Arg.DataType.INTEGER, "2");
		
		p.parse(args);
		
		String option1 = p.getArgValue("type");
		int option2 = p.getArgValue("digits");
		double vol = 0;
		float h = 0;
		float l = 0;
		float w = 0;
		float r = 0;
		//float pi = 3.14f;
		
		switch(option1) {
			case "cube":
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
				l = p.getArgValue("length");
				h = p.getArgValue("height");
				w = p.getArgValue("width");
				vol = l*w*h;
		}
		
		System.out.println("Volume is "+ String.format("%." + Integer.toString(option2) + "f", vol) + ".");
		
		//float l = p.getArgValue("length"));
		//float h = p.getArgValue("height"));
		//float w = p.getArgValue("width"));
		
		
		//System.out.println("Volume is "+Math.round(l*w*h)+".");
	}
}