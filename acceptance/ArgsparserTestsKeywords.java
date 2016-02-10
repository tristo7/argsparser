import edu.jsu.mcis.*;

public class ArgsparserTestsKeywords {
	
	private ArgsParser p = new ArgsParser();
	private String output;
	
	public void startVolumeCalculatorWithArguments(String[] cla){
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		try{
		p.parse(cla);
		float l = Integer.valueOf(getLength());
		float w = Integer.valueOf(getWidth());
		float h = Integer.valueOf(getHeight());
		int vol = Math.round(l*w*h);
		output = String.valueOf(vol);
		}catch(TooManyArgumentsException t){
			output = t.getMessage();
		}
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
		return output;
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
		try{
			p.parse(cla);
		} catch(HelpMessageException h){
			System.out.println("caught the exception.");
			output = h.getMessage();
		}
	}
}