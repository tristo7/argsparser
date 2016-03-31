import edu.jsu.mcis.*;
import java.util.*;
public class PizzaPlace {
	public static void main(String[] args) {
		XMLTools x = new XMLTools();
		ArgsParser p = x.load("Demo2.xml");
		
		
		p.parse(args);
		float subtotal = 0;
		float total = 0;
		String size = "";
		String drink = "";
		size = p.getArgValue("size");
		drink = p.getArgValue("drink");
		
		switch(size.toLowerCase()){
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
		
		switch(drink.toLowerCase()){
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
		total = subtotal*1.10f;
		
		System.out.println("Your subtotal is $" + Float.toString(subtotal) + ".");
		System.out.println("Your total is $" + String.format("%.2f.", total));
		
	}
}