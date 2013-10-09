/*
 * File: CS106ATiles.java
 * Name: Jingxin Zhu
 * ----------------------
 * This file is the starter file for the CS106ATiles problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class CS106ATiles extends GraphicsProgram {
	
	/** Amount of space (in pixels) between tiles */
	private static final int TILE_SPACE = 20;
	
	/* Width of tile */
	private static final int TILE_WIDTH = 160;
	
	/* Height of tile */
	private static final int TILE_HEIGHT = 60;
	
	public void run() {
		
		/* Coordiantes for center position of canvas */
		double center_x = getWidth()/2;
		double center_y  = getHeight()/2;
		
		/* y-coordinate of upper left tile */
		double ty = center_y - TILE_SPACE/2 - TILE_HEIGHT ;
		
		/* draw tiles row by row from up to bottom */
		for(int i=0; i<2; i++){
			
			/* x-coordiante of left tile  of each row */
			double tx = center_x - TILE_SPACE/2 - TILE_WIDTH;
			
			/* draw tiles of i_th row from left to right */
			for(int j=0; j<2; j++){
			/* draw the first tile */
			GRect tile = new GRect(tx, ty, TILE_WIDTH, TILE_HEIGHT);
			
		    GLabel label = new GLabel("CS106A");
		    /* x-coordinate for label */
		    double x = tx+TILE_WIDTH/2 - label.getWidth()/2; 
		    /* y-coordiante for label */
		    double y = ty+TILE_HEIGHT/2 + label.getAscent()/2;  //center position for label 
		    add(tile);
			add(label,x,y);
			tx = tx + TILE_SPACE + TILE_WIDTH;
			}
		ty = ty + TILE_SPACE + TILE_HEIGHT ;
		}	
	}
}

