import edu.jsu.mcis.*;

public class ArgsparserTestsKeywords {
	
	private ArgsParser p = new ArgsParser();
	private String output;
	
	public void startVolumeCalculatorWithArguments(String[] cla){
		p.setProgramName("VolumeCalculator");
		p.addArg("length", Arg.DataType.FLOAT);
		p.addArg("width", Arg.DataType.FLOAT);
		p.addArg("height", Arg.DataType.FLOAT);
		p.addNamedArg("type", Arg.DataType.STRING, "box", 't');
		p.addNamedArg("digits", Arg.DataType.STRING, "4", 'd');
		try{
		p.parse(cla);
		float l = p.getValue("length");
		float w = p.getValue("width");
		float h = p.getValue("height");
		float vol = l*w*h;
		
		output = String.format("%.0f", vol);
		}catch(TooManyArgumentsException t){
			output = t.getMessage();
		}
	}
	
	public String getLength(){
		float temp = p.getValue("length");
		return String.format("%.0f", temp);
	}
	
	public String getWidth(){
		float temp = p.getValue("width");
		return String.format("%.0f", temp);
	}
	
	public String getHeight(){
		float temp = p.getValue("height");
		return String.format("%.0f", temp);
	}
	
	public String getProgramOutput(){
		return output;
	}
	
	public String getType(){
		return p.getValue("type");
	}
	
	public String getDigits(){
		return p.getValue("digits");
	}
	
	public void startAbsurdProgramWithArguments(String[] cla){
		p.addArg("pet");
		p.addArg("number", Arg.DataType.INTEGER);
		p.addArg("rainy", Arg.DataType.BOOLEAN);
		p.addArg("bathrooms", Arg.DataType.FLOAT);
		p.parse(cla);
	}
	
	public String getPet(){
		return p.getValue("pet");
	}
	
	public String getNumber(){
		int temp = p.getValue("number");
		return String.valueOf(temp);
	}
	
	public String getRainy(){
		boolean temp = p.getValue("rainy");
		return String.valueOf(temp);
	}
	
	public String getBathrooms(){
		float temp = p.getValue("bathrooms");
		return String.format("%.1f", temp);
	}
	
	public void startProgramWithArguments(String[] cla){
		p.setProgramName("VolumeCalculator");
		p.setProgramDescription("Calcuate the volume of a box.");
		p.addArg("length", Arg.DataType.FLOAT, "the length of the box (float)");
		p.addArg("width", Arg.DataType.FLOAT, "the width of the box (float)");
		p.addArg("height", Arg.DataType.FLOAT, "the height of the box (float)");
		
		try{
			p.parse(cla);
		} catch(HelpMessageException h){
			output = h.getMessage();
		} catch(InvalidArgumentException i){
			output = i.getMessage();
		}
	}
}