/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	/* set sentinel */
	private static final int SENTINEL = 1;
	public void run() {
		
		/* set counter of this loop */
		int counter = 0;
		
		/* read a number from the user */
		int n = readInt("Enter a number: ");
		
		while( n!= SENTINEL ){
			/* if n is even, divide it by two */
			if (n%2 == 0){
				int half = n/2;
				println(n+" is even, so I take half: "+ half);
				n = half;
			}
			/* if n is odd, multiply it by three and add one */
			else{
				int m = 3*n + 1;
				println(n+" is odd, so I make 3n + 1: "+ m);
				n = m;
			}
			/* counter add one */
			counter += 1;
		}
		println("The process took "+ counter + " to reach "+SENTINEL);
	}
}

