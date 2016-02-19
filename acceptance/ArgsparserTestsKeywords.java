import edu.jsu.mcis.*;

public class ArgsparserTestsKeywords {
	
	private ArgsParser p = new ArgsParser();
	private String output;
	
	public void startVolumeCalculatorWithArguments(String[] cla){
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		p.addOptionalArg("type", Arg.DataType.STRING, "box");
		p.addOptionalArg("digits", Arg.DataType.STRING, "4");
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
		return p.getArgValue("length");
	}
	
	public String getWidth(){
		return p.getArgValue("width");
	}
	
	public String getHeight(){
		return p.getArgValue("height");
	}
	
	public String getProgramOutput(){
		return output;
	}
	
	public String getType(){
		return p.getArgValue("type");
	}
	
	public String getDigits(){
		return p.getArgValue("digits");
	}
	
	public void startAbsurdProgramWithArguments(String[] cla){
		p.addArg("pet");
		p.addArg("number");
		p.addArg("rainy");
		p.addArg("bathrooms");
		p.parse(cla);
	}
	
	public String getPet(){
		return p.getArgValue("pet");
	}
	
	public String getNumber(){
		return p.getArgValue("number");
	}
	
	public String getRainy(){
		return p.getArgValue("rainy");
	}
	
	public String getBathrooms(){
		return p.getArgValue("bathrooms");
	}
	
	public void startProgramWithArguments(String[] cla){
		p.addArg("length", Arg.DataType.FLOAT);
		p.addArg("width", Arg.DataType.FLOAT);
		p.addArg("height", Arg.DataType.FLOAT);
		
		try{
			p.parse(cla);
		} catch(HelpMessageException h){
			output = h.getMessage();
		} catch(InvalidArgumentException i){
			output = i.getMessage();
		}
	}
}