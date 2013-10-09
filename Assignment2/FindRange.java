/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import com.sun.xml.internal.rngom.digested.DDataPattern;

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	/* sentinel for this program */
	private static final int SENTINEL = 0;
	
	public void run() {
		println("This program finds the largest and smallest numbers. ");
		int x = readInt("? ");
		
		/* if the first input is sentinel, stop the program */
		if (x == SENTINEL){
			println("The very first input is SENTINEL");
		}
		
		else{
			/* set the first input to be both the largest and the smallest number */
			int max = x;
			int min = x;
			
			int y = readInt("? ");
			
			while(y != SENTINEL){
				max = Math.max(x, y);
				min = Math.min(x, y);
				y = readInt("? ");
			}
			println("smallest: " + min);
			println("largest: " + max);
		}
		
	}
}

