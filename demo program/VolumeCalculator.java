import edu.jsu.mcis.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.addArg("length", Arg.DataType.FLOAT);
		p.addArg("width", Arg.DataType.FLOAT);
		p.addArg("height", Arg.DataType.FLOAT);
		
		p.parse(args);
		
		float l = p.getArgValue("length"));
		float h = p.getArgValue("height"));
		float w = p.getArgValue("width"));
		
		System.out.println("Volume is "+Math.round(l*w*h)+".");
	}
}