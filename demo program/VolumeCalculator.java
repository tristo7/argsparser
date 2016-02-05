public class VolumeCalculator {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		p.getArgs();
		
		try {
			float length = Float.parseFloat(args[0]);
			float width = Float.parseFloat(args[1]);
			float height = Float.parseFloat(args[2]);
			float volume = length * width * height;
			System.out.println("The volume is: " + volume);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			
		}
	}
}