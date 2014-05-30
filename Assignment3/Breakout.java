/*
 * File: Breakout.java
 * -------------------
 * Name: Jingxin Zhu
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;



public class Breakout extends GraphicsProgram {

    /** Width and height of application window in pixels.  On some platforms 
     * these may NOT actually be the dimensions of the graphics canvas. */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /** Dimensions of game board.  On some platforms these may NOT actually
     * be the dimensions of the graphics canvas. */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 30;

    /** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks */
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 4;

    /** Width of a brick */
    private static final int BRICK_WIDTH =
        (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Number of turns */
    private static final int NTURNS = 3;

    /** Time for displaying the ball */
    private static final int DELAY = 8;


    /* Method: run() */
    /** Runs the Breakout program. */
    public void run() {

        /* Part 1: Setup Game */
        num = NBRICKS_PER_ROW * NBRICK_ROWS;

        round = NTURNS;   // record the round number

        // set the bricks, paddle, and label for the game
        // and initialize the score from 0
        // record the number of remaining bricks
        setupGame();

        addMouseListeners();

        /* Part 2: Play Game */
        flag = false;
        while (round > 0){
            playGame(); 
            if(flag == true){  
                break;
            }
            round -= 1;
        }

        /* Part 3: Finish Game */
        if (flag == false) {
            gameover();  // if none rounds left, you lose
        }	
        else{
            youWin();    // if none bricks left, you win
        }
    }

    /** Part 1. Set up game */
    private void setupGame(){

        // 1.1. add bricks
        addBricks(Color.RED, 	0);
        addBricks(Color.ORANGE, 1);
        addBricks(Color.YELLOW, 2);
        addBricks(Color.GREEN, 	3);
        addBricks(Color.CYAN, 	4);

        // 1.2. create paddle
        createPaddle();

        // 1.3. scores for the game, starting from 0
        scoreForGame();

        // 1.4. remaining rounds for the game
        roundForGame();

    }	

    /* 1.1. Set up bricks */
    private void addBricks(Color color, int k) { 	
        /* y coordinate for the first row of bricks */
        int y = BRICK_Y_OFFSET + 2 * (BRICK_HEIGHT + BRICK_SEP) * k;
        /* add RED bricks */
        for(int j=0; j<2; j++){
            int x = (WIDTH - BRICK_WIDTH * NBRICK_ROWS - (NBRICKS_PER_ROW - 1) * BRICK_SEP)/2;
            for (int i=0; i< NBRICK_ROWS;i++){
                brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                brick.setColor(color);
                brick.setFilled(true);
                brick.setFillColor(color);
                add(brick);
                x += BRICK_WIDTH + BRICK_SEP;
            }
            y += BRICK_HEIGHT + BRICK_SEP;
        }
    }

    /* 1.2. Create the paddle */
    private void createPaddle(){
        double x = ( getWidth() - PADDLE_WIDTH )/2;
        double y = HEIGHT -  PADDLE_Y_OFFSET;
        paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setFillColor(Color.BLACK);
        add(paddle);
    }

    /* 1.3. scores for the game */
    private void roundForGame(){
        int round = 3;
        roundLabel = new GLabel("");
        roundLabel.setFont("Times New Roman-16");
        add(roundLabel, 10, roundLabel.getHeight());
        roundLabel.setLabel("Rounds left: " + round);
    }

    /* 1.4. remaining rounds for game */
    private void scoreForGame(){
        scores = 0;
        score = new GLabel("");
        score.setFont("Times New Roman-16");
        add(score, getWidth() - 1.5*PADDLE_WIDTH, score.getHeight());
        score.setLabel("Scores: " + scores);
    }

    /** Part2: Get game started */
    private void playGame(){

        roundLabel.setLabel("Rounds left: " + round);

        // 2.1. add start label
        addLabel();
        waitForClick();
        // 2.2. create the ball
        createBall(BALL_RADIUS);

        // 2.3. get the ball bounced
        bounceBall();

    }

    /* click to start game */
    public void mouseClicked(MouseEvent e){
        remove(click);
    }
    public void mouseMoved(MouseEvent e){
        paddle.move(lastX - paddle.getX() - PADDLE_WIDTH/2, 0);
        if( lastX < PADDLE_WIDTH )  
            paddle.move(0, 0);
        else{
            if( lastX > (WIDTH-PADDLE_WIDTH) ) 
                paddle.move(0, 0);
            else{
                paddle.move(e.getX() - lastX, 0);
            }
        }
        lastX = e.getX();
        lastY = e.getY();
    }

    /* 2.1. Label for start game */
    private void addLabel(){
        click = new GLabel("Click mouse to start!");
        click.setFont("Time-24");
        add(click, (getWidth() - click.getWidth())/2, (getHeight() - click.getAscent())/2);
    }

    /* 2.2. Create the ball */
    private void createBall(int radius){
        int x = WIDTH/2 - radius;
        int y = HEIGHT/2 - radius;
        ball = new GOval(x, y, 2*radius, 2*radius);
        ball.setFilled(true);
        ball.setColor(Color.BLACK);
        add(ball);
    }

    /* 2.3. update and move ball */
    private void bounceBall(){
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) vx = -vx;
        vy = 3.0;
        while(true){
            ball.move(-vx, vy);
            // 2.2.1
            checkForBounce();
            if (ball.getY()  >= getHeight() - PADDLE_Y_OFFSET) break;
            // 2.2.2
            checkForCollisions();
            if (num == -1){
                flag = true;
                break;
            }
            pause(DELAY);
        }
        remove(ball);
    }

    /* 2.3.1 check for bouncing off the boundaries */
    private void checkForBounce(){
        // check for right boundary and left boundary
        if (ball.getX() + 2 * BALL_RADIUS >= WIDTH) vx = -vx;
        if (ball.getX() <= 0) vx = -vx;
        // check for upper wall
        if (ball.getY() <= 0) vy = -vy;
    }

    /* 2.3.2 check for collisions between ball and brick/paddle */
    private void checkForCollisions(){
        collider = getCollidingObject();
        if (collider != null){
            if (collider == paddle ) vy = -vy;
            else if(collider == roundLabel || collider == score){
                vy = vy;
            }
            else{     	// else it would be a brick
                num -= 1;
                vy = -vy;
                calculateScore();
                score.setLabel("Scores: " + scores);
                remove(collider);	
            }
        }
    }

    private GObject getCollidingObject(){
        // check the upper left point
        gobj = getElementAt(ball.getX(), ball.getY());
        if ( gobj != null)  
            gobj = getElementAt(ball.getX(), ball.getY());
        else{
            // check the upper right point
            gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
            if ( gobj != null) {
                gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
            }
            else{
                // check the lower left point
                gobj = getElementAt(ball.getX(), ball.getY()  + 2 * BALL_RADIUS);	
                if ( gobj != null) {
                    gobj = getElementAt(ball.getX(), ball.getY()  + 2 * BALL_RADIUS);	
                }
                else{
                    // check the lower right point
                    gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()  + 2 * BALL_RADIUS);	
                    if ( gobj != null) {
                        gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()  + 2 * BALL_RADIUS);	
                    }
                    else gobj = null;
                }
            }
        }
        return gobj;
    }

    private void calculateScore(){
        if (collider.getColor() == Color.RED ) scores += 10;
        if (collider.getColor() == Color.ORANGE ) scores += 8;
        if (collider.getColor() == Color.YELLOW ) scores += 5;
        if (collider.getColor() == Color.GREEN ) scores += 2;
        if (collider.getColor() == Color.CYAN ) scores += 1;	
    }

    /** Part 3: Finishing game */
    /* if none rounds left*/
    private void gameover(){

        remove(paddle);
        remove(score);
        remove(roundLabel);
        addLoseLabel();			
    }

    private void addLoseLabel(){
        GLabel loseGLabel  = new GLabel("Oh, You lose!");
        loseGLabel.setFont("Time-24");
        add(loseGLabel, (getWidth() - loseGLabel.getWidth())/2, (getHeight() - loseGLabel.getAscent())/2);
    }

    /* if none bricks left */
    private void youWin(){
        remove(roundLabel);
        remove(score);
        GLabel win = new GLabel("You win!");
        win.setFont("Time-72");
        add(win, (getWidth() - win.getWidth())/2, (getHeight() + win.getAscent())/2);
        remove(click);
    }

    /** Private instance variables */
    private boolean flag;
    private int num, scores;
    private GLabel score, click, roundLabel;
    private GRect paddle, brick;
    private GObject collider, gobj;
    private GOval ball;
    private double lastX, lastY;
    private double vx, vy;
    private int round;
    private RandomGenerator rgen = RandomGenerator.getInstance();

}
