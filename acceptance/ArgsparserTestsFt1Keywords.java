import edu.jsu.mcis.*;

public class ArgsparserTestsFt1Keywords {
	
	private ArgsParser p = new ArgsParser();
	
	
	public void startVolumeCalculatorWithArguments(String[] cla){
		
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		p.parse(cla);
	}
	
	public String getLength(){
		return p.getArg("length");
	}
	
	public String getWidth(){
		return p.getArg("width");
	}
	
	public String getHeight(){
		return p.getArg("height");
	}
	
	public String getProgramOutput(){
		int l = Integer.valueOf(getLength());
		int w = Integer.valueOf(getWidth());
		int h = Integer.valueOf(getHeight());
		
		int vol = l*w*h;
		return String.valueOf(vol);
		
	}
	
	
}