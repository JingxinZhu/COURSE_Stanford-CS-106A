/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.*;
import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
		
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
		
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
		playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

/** The number of initial values of each category except upper_score, upper_bonus, lower_score and total score */	
	public static final int Init_score = 1000;
	
	private void playGame() {
		
		/*******  Initialization For the Game  *********/		
		// Initialize score records for all players
		int[][] scores_record = initializeScores();
		
		/***************   Play Game   ***************/
		/* Play the game turn by turn */ 
		for(int i=0; i<N_SCORING_CATEGORIES;i++){
			/* Play one turn player by player */ 
			for(int j=0; j<nPlayers; j++){
				
				// Initialize score for this turn of this particular player
				int score;  
				// Display which player is playing the game
				display.printMessage(playerNames[j] + " 's turn! Click \"Roll Dice\" button to roll the dice. ");
				// Prompt player to start rolling dice
				display.waitForPlayerToClickRoll(j+1);
			
				/* Step 1:  Player rolls dice for the first time, then display dice values */ 
				int[] dice = newDice();
				display.displayDice(dice);
			
				/* Step 2:  Player can re-roll dice for two more chances */
				reRoll(dice);
				
				/* Step 3:  Player selects an unused category for his turn  */
				display.printMessage("Select a category for this roll.");
				int category = selectUnusedCategory(j,scores_record);
				
				/* Step 4: Calculate score of the player for this turn */
				boolean p = checkCategory(dice, category);  
				if (p) score = calculateScore(category, dice, scores_record);
				else score = 0;
		
				// update category score, total, upper and lower score for this player
				scores_record[category-1][j] = score;
				scores_record[TOTAL-1][j] += score; 		   // update total score
				if(category >=1 && category <=6)     		   // update upper score
					scores_record[UPPER_SCORE-1][j] += score;
				else scores_record[LOWER_SCORE-1][j] += score; // update lower score
						 	
				/* Step 5: Record the result of this turn */
				display.updateScorecard(category, j+1, score);     // update selected category	
				display.updateScorecard(TOTAL, j+1, scores_record[TOTAL-1][j]);  // update total score
			}
		}
		
		/**************     Find winner   ***************/
		// Check whether players can earn upper bonus, display 
		for(int j=0;j<nPlayers; j++){
			if( scores_record[UPPER_SCORE-1][j] >= 63 )  // if upper scores end up totaling 63 or more, 
				scores_record[UPPER_BONUS-1][j] = 35;	 // then player earns 35 bonus points
			scores_record[TOTAL-1][j] += scores_record[UPPER_BONUS-1][j]; // add bonus points to total scores
			
			display.updateScorecard(UPPER_SCORE, j+1, scores_record[UPPER_SCORE-1][j]);
			display.updateScorecard(UPPER_BONUS, j+1, scores_record[UPPER_BONUS-1][j]);
			display.updateScorecard(LOWER_SCORE, j+1, scores_record[LOWER_SCORE-1][j]);
			display.updateScorecard(TOTAL, j+1, scores_record[TOTAL-1][j]);
		}
		
		int winner = findWinner(scores_record);
		display.printMessage("Congratulations, " + playerNames[winner] + " , you're the winner with a total score of " + scores_record[TOTAL-1][winner]);
	}
	
	
	/***************************************/
	/**                                   **/
	/**         Private Functions         **/
	/**                                   **/
	/***************************************/
	

	/*******  Functions for Initialization For the Game  *********/
	/* Process : create an nPlayer-dimensional array with each row standing for scores of a category 
	 * 			 for all players; each column standing for a player's scores for all categories. 
	 * 			 For categories of Upper score, upper bonus, lower score and total, initial scores are 0; 
	 * 			 for other categories, initial values are 1000.
	 * Output  : score records, a multidimensional array recording initial scores for all players
	 */
	private int[][] initializeScores(){
		int[][] scores_record = new int[N_CATEGORIES][nPlayers];
		for (int i=0; i<N_CATEGORIES; i++){
			for(int j=0; j<nPlayers; j++){
				if(i==UPPER_SCORE-1 || i==UPPER_BONUS-1 || i==LOWER_SCORE-1 || i==TOTAL-1) 
					scores_record[i][j] = 0;
				else scores_record[i][j] = Init_score;
			}
		}
		return scores_record;
	}
	
	/**************   Functions for Play Game   ***************/
	/*          Step 1: roll dice for the first time         */ 
	/* Process : Randomly generate five dice values for player's first roll
	 * Output  : rgendice, an array containing five dice values
	 */
	private int[] newDice(){
		int[] rgendice = new int[N_DICE];
		for (int i=0; i<N_DICE; i++){
			rgendice[i] = rgen.nextInt(1, 6);
		}
		return rgendice;
	}
				
	/* Step 2: allow player to re-roll dice they select for two more chances */ 
	/* Input   : dice, the array containing dice values generated by the first roll
	 * Process : allow user to choose any dice they want to re-roll for another two chances
	 * 			 if player does not want to change dice, simply click "roll again"
	 * Output  : void
	 */
	private void reRoll(int[] dice){
		for (int i=0; i<2; i++){
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again \".");
			// Allow player to select dice for re-rolling
			display.waitForPlayerToSelectDice();
			// Update dice values after re-rolling
			updateSelectedDice(dice);
			// Display updated dice values 
			display.displayDice(dice);
		}
	}

	/*  Update dice for re-roll  */
	private void updateSelectedDice(int[] dice) {
		for (int i=0; i<N_DICE; i++){
			if( display.isDieSelected(i) ){					
			    //randomly generate new dice
				dice[i] = rgen.nextInt(1,6);
			}
		}
	}
				
	/* Step 3: make sure the player choose an unused category */
	
	/* Input   : player, the index of this player; scores_record, the array containing score records
	 * Process : if the category has not been selected, return the category player selected; 
	 * 			 otherwise, prompt the player to choose another category.
	 * Output  : select, the unused category that player has selected
	 */
	private int selectUnusedCategory(int player,int[][] scores_record){
		// let player to choose a category 
		int select = display.waitForPlayerToSelectCategory();
		while ( scores_record[select-1][player] != Init_score ) { 
			display.printMessage("This category has been selected, please choose another category. ");
			select = display.waitForPlayerToSelectCategory();
		}
		return select;
	}
		
	/* Step 4: calculate score for this turn */
	/* Input   : category, the category player selects; dice, the array containing dice values
	 * Process : calculate score for that category
	 * Output  : score, the score for category player selected
	 */
	private int calculateScore(int category, int[] dice, int[][] scores_record){
		int score = 0;
		// Ones, Twos, Threes, Fours, Fives, Sixes 
		if ( category <=SIXES && category >=ONES ){
			for(int i=0; i< N_DICE; i++){
				if ( dice[i] == category ) score += category;
			}
		}
		// Three of a kind, Four of a kind, Chance
		if ( category==THREE_OF_A_KIND || category==FOUR_OF_A_KIND || category==CHANCE ){
			for(int i=0; i< N_DICE; i++){
				score += dice[i];
			}
		}
		if ( category == FULL_HOUSE ) score = 25;  // Full House
		if ( category == SMALL_STRAIGHT ) score = 30;  // Small Straight
		if ( category == LARGE_STRAIGHT ) score = 40;  // Large Straight
		if ( category == YAHTZEE ) score = 50;  // Yahtzee!
		return score;
	} 
						
	/* Find winner of the game */
	/* Input   : total, the array containing all players' total score
	 * Process : find the maximum score and the player who earns that score
	 * Output  : index, indicating the winner's index
	 */
	private int findWinner(int[][] scores_record){
		// Find index of maximum score among all players
		int max = scores_record[TOTAL-1][0];
		int index = 0;
		for (int i=1; i<nPlayers; i++){
			if ( scores_record[TOTAL-1][i] > max ) {
				max = scores_record[TOTAL-1][i];
				index = i;
			}
		}
		return index;
	}
			
			
	private boolean checkCategory(int[] dice, int category){
		boolean category_valid = false ;
		// Create a hash map with each element standing for 
		// the number of a certain die value in the dice configuration
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i=0; i<N_DICE; i++){
			Integer count = map.get(dice[i]);
			if ( count == null ) count = 1;
			else count++;
			map.put(dice[i], count);
		}
		//	Any dice configurations is valid for 
		//  Ones, Twos, Threes, Fours, Fives, Sixes, and Chance
		if ( ( category>=ONES && category<=SIXES ) || category==CHANCE ) 
			category_valid = true;    
		if ( category == THREE_OF_A_KIND ) // at least three of the dice show the same value
			category_valid = map.containsValue(3) || map.containsValue(4) || map.containsValue(5); 
		if ( category == FOUR_OF_A_KIND )  // at least four of the dice show the same value
			category_valid = map.containsValue(4) || map.containsValue(5);
		if ( category == YAHTZEE )			// all of the dice must show the same value
			category_valid = map.containsValue(5); 
		if ( category == FULL_HOUSE )		// three of one value and two of another value
			category_valid = map.containsValue(2) && map.containsValue(3);
		if ( category == SMALL_STRAIGHT )   // the dice contains at least four consecutive values 
			category_valid = ( map.containsKey(1) && map.containsKey(2) && map.containsKey(3) && map.containsKey(4) )
						 ||  ( map.containsKey(2) && map.containsKey(3) && map.containsKey(4) && map.containsKey(5) ) 
						 ||  ( map.containsKey(3) && map.containsKey(4) && map.containsKey(5) && map.containsKey(6) );
		if ( category == LARGE_STRAIGHT )  // the dice contains five consecutive values
			category_valid = ( map.containsKey(1) && map.containsKey(2) && map.containsKey(3) && map.containsKey(4) && map.containsKey(5) )
					       ||( map.containsKey(2) && map.containsKey(3) && map.containsKey(4) && map.containsKey(5) && map.containsKey(6) );
		return category_valid;
	}
	
	
					
	/* Private instance variables */
	    private int nPlayers;
		private String[] playerNames;
		private YahtzeeDisplay display;
		private RandomGenerator rgen = new RandomGenerator();

	}


