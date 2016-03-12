package edu.jsu.mcis;

import java.util.*;

public class CustomizedComparator implements Comparator<Arg> {
	
	@Override
	public int compare(Arg a1, Arg a2) {
		int comp =  Integer.compare(a1.getPosition(), a2.getPosition());
		return comp;
	}
}