/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	
	/* define conversion from inch to pixel */
	private static final int INCH = 72;
	
	public void run() {
		
		/* radius of the outer circle */
		double outer_r = 1*INCH;
		/* radius of the inner circle */
		double inner_r = 0.3*INCH;
		/* radius of the white circle between outer and inner circles */
		double white_r = 0.65*INCH;
		
		/* coordinates of the center position */
		double center_x = getWidth()/2;
		double center_y = getHeight()/2;
		
		GOval outer = new GOval(center_x-outer_r,center_y-outer_r,2*outer_r,2*outer_r);
		outer.setColor(Color.white);
		outer.setFillColor(Color.RED);
		outer.setFilled(true);
		add(outer);
		GOval white = new GOval(center_x-white_r,center_y-white_r,2*white_r,2*white_r);
		white.setColor(Color.white);
		white.setFillColor(Color.white);
		white.setFilled(true);
		add(white);
		GOval inner = new GOval(center_x-inner_r,center_y-inner_r,2*inner_r,2*inner_r);
		inner.setColor(Color.white);
		inner.setFillColor(Color.RED);
		inner.setFilled(true);
		add(inner);
		
		
	}
}
