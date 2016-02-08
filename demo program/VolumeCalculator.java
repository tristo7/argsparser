import java.util.*;

public class VolumeCalculator {
	public static void main(String[] args) {
		/*
		ArgsParser p = new ArgsParser();
		p.addArg("length");
		p.addArg("width");
		p.addArg("height");
		p.parse(args);
		*/
		
		
		//Convert the String array into 
		String s = Arrays.toString(args);
		s = s.replace("[","");
		s = s.replace("]","");
		s = s.replace(",","");
	
		System.out.println(s);
		System.out.println(s.length());
	}
}