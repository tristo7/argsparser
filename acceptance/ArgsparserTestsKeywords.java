import edu.jsu.mcis.*;

public class ArgsparserTestsKeywords {
	
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
	
	public void startAbsurdProgramWithArguments(String[] cla){
		p.addArg("pet");
		p.addArg("number");
		p.addArg("rainy");
		p.addArg("bathrooms");
		p.parse(cla);
	}
	
	public String getPet(){
		return p.getArg("pet");
	}
	
	public String getNumber(){
		return p.getArg("number");
	}
	
	public String getRainy(){
		return p.getArg("rainy");
	}
	
	public String getBathrooms(){
		return p.getArg("bathrooms");
	}
	
	public void startProgramWithArguments(String[] cla){
		p.parse(cla);
	}
}