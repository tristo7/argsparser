import edu.jsu.mcis.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		
		p.parse(s);
		
		float l = Integer.valueOf(p.getArg("length"));
		float w = Integer.valueOf(p.getArg("width"));
		float h = Integer.valueOf(p.getArg("height"));
		
		System.out.println("Volume is "+l*w*h+".");
	}
}