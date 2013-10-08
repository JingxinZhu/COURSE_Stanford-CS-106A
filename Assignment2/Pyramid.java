/*
 * File: Pyramid.java
 * Name: Jingxin Zhu
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Height of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		
		/* x-coordinate for the most left brick of the bottom row */
		float begin_x = getWidth()/2 - (BRICKS_IN_BASE/2)*BRICK_WIDTH;
		
		/* y-coordinate for the most left brick of the bottom row */
		float begin_y = getHeight() - BRICK_HEIGHT;
		
		/* add bricks row by row from bottom to up */
		for (int i=BRICKS_IN_BASE; i>0; i--){
			
			/* x-coordinates for the first brick of bottom row */
			float x = begin_x;
			
			/*add bricks of a specific row from left to right*/
			for (int j=0; j<i; j++){	
				GRect brick  = new GRect(x, begin_y, BRICK_WIDTH, BRICK_HEIGHT );
				add(brick);
				/* x-coordinate for next brick */
				x = x + BRICK_WIDTH;
			}
		/* x-coordinate for the first brick of upper row */
		begin_x = begin_x+(BRICK_WIDTH/2);
		/* y-coordinate for bricks of upper row*/
		begin_y = begin_y - BRICK_HEIGHT;
		}
	}
}

