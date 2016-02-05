package edu.jsu.mcis;


public class Arg{
private String argName;
private String value;

	public Arg(String n){
		argName = n;
	}

	public String getName(){
		return argName;
	}
	
	public void setName(String n){
		argName = n;
	}
	
	public String getValue(){
		return val;
	}
	
	public void setValue(String v){
		value = v;
	}
}