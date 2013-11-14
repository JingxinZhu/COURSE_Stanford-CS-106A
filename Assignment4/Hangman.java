/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.PRIVATE_MEMBER;

public class Hangman extends ConsoleProgram {

	
	private static final int GUESS_TIMES = 8;
	 
	  /* add canvas to program */
	 public void init(){
	    	canvas = new HangmanCanvas();
	    	add(canvas);
	    }

	 
	 
	 
    public void run() {
    	// choose a random word as the secret number
    	//secret = HangmanLexicon.getWord(rgen.nextInt(1, 9));
    	HangmanLexicon secret_word = new HangmanLexicon();
    	int words_number = secret_word.getWordCount();
    	secret = secret_word.getWord(rgen.nextInt(1, words_number));
    	num_left = secret.length();
    	
    	canvas.reset();
    	
    	initialize();
    	
		userGuess();
		
		
	}
    
    
    
    
    
    private void initialize(){
    	
    	secret = secret.toUpperCase();
    	println("Welcome to Hangman.");
    	// showing the initial with ----
    	remain = "";
    	for( int i=0; i<secret.length(); i++ ){
    		remain += "-";
    	}
    	
    	println("The word now looks like this: " + remain);
    
    }
    
    
    private void userGuess(){
    	chances_left = GUESS_TIMES;
    	boolean flag = false;
    	while( chances_left >0){
    	
    		    		
    		println("You have " + chances_left + " guesses left.");
    		
    		// input from the user
    		guess = readLine("You guess: ");
    		guess = guess.toUpperCase();
    		// check whether input is a single letter
    		checkInput();
    		
    		// check whether letter is in secret word
    		checkGuessInSecret();

    		// showing remaining result;
    		checkRemaining();
    		
    		
    		// game result
    		if (num_left == 0) {
    			flag = true;
    			break;
    		}

    	}
    	if(flag == true){
    		println("You guess the word: " + secret);
    		println("You win.");
    	}
    	else{
    		println("You are completely hung.");
    		println("The secret word is: " + secret);
    	}
    }
    
    
    
    private void checkInput(){
    	while(true){
    		char ch = guess.charAt(0); 
    		// if input is a single letter, it's valid
    		if ( (Character.isLetter(ch)) && (guess.length() == 1) )
    			break;
    		else{
    			println("Your input is invalid, please enter a single letter.");
    			guess = readLine("Your guess: ");

    		}
    	}
	}
    
    
    private void checkGuessInSecret(){
    	// accept user's guess in either lower or upper case
    	guess.toLowerCase();
    	int n = secret.indexOf(guess);
    	if (n == -1){
    		println("There are no " + guess.toUpperCase() + "'s in the word.");
    		canvas.noteIncorrectGuess(guess.charAt(0));
    		chances_left --;
    	}
    	else{		
    		println("That guess is correct.");
    		
    		num_left --;
    	} 	
    }
    
    /* checkRemaining() */
    private void checkRemaining(){
    	int n;
    	int m = remain.indexOf(guess);
    	if (m != -1) {
    		n = secret.indexOf(guess, m+1) ;
       	}else {
    		n = secret.indexOf(guess);
    	}
    	if (n != -1){
       	remain = remain.substring(0, n) + guess + remain.substring(n+1);}
    	canvas.displayWord(remain);
    	println("The word now looks like this: " + remain);
    }
    
   
  
    
   
    
    
    
    /* private instant variables */
    private int num_left;
    private int chances_left;
    private String remain;
    private String secret;
    private String guess;
    private HangmanCanvas canvas;
    private RandomGenerator rgen = RandomGenerator.getInstance();

}
