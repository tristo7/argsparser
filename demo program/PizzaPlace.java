import edu.jsu.mcis.*;
import java.util.*;

public class PizzaPlace {
	public static void main(String[] args) {
		ArgsParser p = new ArgsParser();
		XMLTools x = new XMLTools();
		
		/* p.setProgramName("PizzaPlace");
		p.setProgramDescription("Order Pizza(s) and a drink!");
		p.addArg("size", Arg.DataType.STRING, "Pizza size: small ($5), medium ($7), or large ($10).");
		p.addArg("drink", Arg.DataType.STRING, "Drinks: coke ($2), pepsi ($2), tea ($1), or water (free).");
		
		p.addOptionalArg("ExtraCheese", Arg.DataType.BOOLEAN, "false", 'c');
		p.getArg("ExtraCheese").setArgDescription("($2)");
		p.addOptionalArg("NoPepperoni", Arg.DataType.BOOLEAN, "false", 'p');
		p.addOptionalArg("PizzaQuantity", Arg.DataType.INTEGER, "1", 'q'); */
		
		
		x.load("Demo2.xml");
		//x.save(p, "Demo2.xml");

		
		float subtotal = 0;
		String size = "";
		String drink = "";
		size = p.getArgValue("size"); //get an argument size does not exist error
		drink = p.getArgValue("drink");
		
		try{
			switch(size){ //get a null pointer exception.
				case "small":
					subtotal += 5;
					break;
				case "medium":
					subtotal += 7;
					break;
				case "large":
					subtotal += 10;
					break;
				default:
					System.out.println("Invalid size. Please try again.");
					System.exit(1);
			}
			if((boolean) p.getArgValue("ExtraCheese")){
				subtotal += 2;
			}
			subtotal = subtotal * (int) p.getArgValue("PizzaQuantity");
			
			switch(drink){
				case "coke":
				case "pepsi":
					subtotal += 2;
					break;
				case "tea":
					subtotal++;
					break;
				case "water":
					break;
				default:
					System.out.println("Invalid drink. Please try again.");
					System.exit(1);
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		System.out.println("Your subtotal is $" + Float.toString(subtotal) + ".");
		System.out.println("Your total is $" + Float.toString(subtotal*1.09f) + ".");
		
	}
}