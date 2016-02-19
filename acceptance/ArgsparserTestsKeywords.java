import edu.jsu.mcis.*;

public class ArgsparserTestsKeywords {
	
	private ArgsParser p = new ArgsParser();
	private String output;
	
	public void startVolumeCalculatorWithArguments(String[] cla){
		p.addArg("length", Arg.DataType.FLOAT);
		p.addArg("width", Arg.DataType.FLOAT);
		p.addArg("height", Arg.DataType.FLOAT);
		p.addOptionalArg("type", Arg.DataType.STRING, "box");
		p.addOptionalArg("digits", Arg.DataType.STRING, "4");
		try{
		p.parse(cla);
		float l = p.getArgValue("length");
		float w = p.getArgValue("width");
		float h = p.getArgValue("height");
		float vol = l*w*h;
		
		output = String.format("%.0f", vol);
		}catch(TooManyArgumentsException t){
			output = t.getMessage();
		}
	}
	
	public String getLength(){
		float temp = p.getArgValue("length");
		return String.format("%.0f", temp);
	}
	
	public String getWidth(){
		float temp = p.getArgValue("width");
		return String.format("%.0f", temp);
	}
	
	public String getHeight(){
		float temp = p.getArgValue("height");
		return String.format("%.0f", temp);
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
		p.addArg("number", Arg.DataType.INTEGER);
		p.addArg("rainy", Arg.DataType.BOOLEAN);
		p.addArg("bathrooms", Arg.DataType.FLOAT);
		p.parse(cla);
	}
	
	public String getPet(){
		return p.getArgValue("pet");
	}
	
	public String getNumber(){
		int temp = p.getArgValue("number");
		return String.valueOf(temp);
	}
	
	public String getRainy(){
		boolean temp = p.getArgValue("rainy");
		return String.valueOf(temp);
	}
	
	public String getBathrooms(){
		float temp = p.getArgValue("bathrooms");
		return String.format("%.1f", temp);
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