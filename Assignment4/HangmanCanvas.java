/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Canvas;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	int n = 1;
	String incorrest_guessString = "";

	
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
			removeAll();
			addScaffold();
			addBeam();
			addRope();
			display_word  = new GLabel ("");
			display_word.setFont("Times New Roman-20");
			add(display_word, 30, getHeight() - 50);
			incorrect_guess  = new GLabel ("");
			incorrect_guess.setFont("Times New Roman-20");
			add(incorrect_guess, 30, getHeight() - 25);
	}
	


/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		
		display_word.setLabel(word);
		
	}
	
	
	

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	
	public void noteIncorrectGuess(char letter) {
		switch (n){
			case 1: addHead(); break;
			case 2: addBody(); break;
			case 3: addLeftArm(); break;
			case 4: addRightArm(); break;
			case 5: addHip(); addLeftLeg();break;
			case 6: addRightLeg(); break;
			case 7: addLeftFoot(); break;
			case 8: addRightFoot(); break;
		}
		n++ ;
		// convert char letter into string
		incorrest_guessString += String.valueOf(letter).toUpperCase();
		incorrect_guess.setLabel(incorrest_guessString) ;
	}
	
	
	// add scaffold
	private void addScaffold() {
		double x0 = getWidth()/2 - BEAM_LENGTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET;
		double y1 = y0 + SCAFFOLD_HEIGHT;
		GLine scaffold = new GLine(x0, y0, x0, y1);
		add(scaffold);
	}
	
	// add beam
	private void addBeam() {
		double x0 = getWidth()/2 - BEAM_LENGTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET;
		double x1 = x0 + BEAM_LENGTH;			
		GLine beam = new GLine(x0, y0, x1, y0);
		add(beam);
	}
	
	// add rope
	private void addRope() {
		double x0 = getWidth()/2;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET;
		double y1 = y0 + ROPE_LENGTH;			
		GLine rope = new GLine(x0, y0, x0, y1);
		add(rope);
	}
	
	
	// add head 
	private void addHead(){
		double x = getWidth()/2 - HEAD_RADIUS;
		double y = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH;
		GOval head = new GOval(x, y, 2*HEAD_RADIUS, 2*HEAD_RADIUS);
		add(head);
	}
	
	// add body
	private void addBody() {
		double x0 = getWidth()/2;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS;
		double y1 = y0 + BODY_LENGTH;			
		GLine body = new GLine(x0, y0, x0, y1);
		add(body);
	}
	
	// add left arm
	private void addLeftArm(){
		double x0 = getWidth()/2;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		double x1 = x0 - UPPER_ARM_LENGTH;			
		GLine left_upper_arm = new GLine(x0, y0, x1, y0);
		double y1 = y0 + LOWER_ARM_LENGTH;
		GLine left_lower_arm = new GLine(x1, y0, x1, y1);
		add(left_upper_arm);
		add(left_lower_arm);
	}
	
	
	// add right arm
	private void addRightArm(){
		double x0 = getWidth()/2;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		double x1 = x0 + UPPER_ARM_LENGTH;			
		GLine right_upper_arm = new GLine(x0, y0, x1, y0);
		double y1 = y0 + LOWER_ARM_LENGTH;
		GLine right_lower_arm = new GLine(x1, y0, x1, y1);
		add(right_upper_arm);
		add(right_lower_arm);
	}
	
	// add hip
	private void addHip(){
		double x0 = getWidth()/2 -HIP_WIDTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH;
		double x1 = x0 + 2*HIP_WIDTH;
		GLine hip = new GLine(x0, y0, x1, y0);
		add(hip);
	}
	
	// add left leg
	private void addLeftLeg() {
		double x0 = getWidth()/2 -HIP_WIDTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH;
		double y1 = y0 + LEG_LENGTH;			
		GLine left_leg = new GLine(x0, y0, x0, y1);
		add(left_leg);
	}
	
	// add right leg
	private void addRightLeg() {
		double x0 = getWidth()/2 + HIP_WIDTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH;
		double y1 = y0 + LEG_LENGTH;			
		GLine right_leg = new GLine(x0, y0, x0, y1);
		add(right_leg);
	}

	// add left foot
	private void addLeftFoot(){
		double x0 = getWidth()/2 -HIP_WIDTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		double x1 = x0 - FOOT_LENGTH;
		GLine left_foot = new GLine(x0, y0, x1, y0);
		add(left_foot);
	}
	
	// add right foot
	private void addRightFoot(){
		double x0 = getWidth()/2 + HIP_WIDTH;
		double y0 = getHeight() - SCAFFOLD_HEIGHT - OFF_SET + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		double x1 = x0 + FOOT_LENGTH;
		GLine right_foot = new GLine(x0, y0, x1, y0);
		add(right_foot);
	}
	

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int OFF_SET = 90;

	/* instance variables */
	private GLabel display_word;
	private GLabel incorrect_guess;
	
	

}
