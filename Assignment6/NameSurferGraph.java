/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	
/* Private instance variable */	
	private ArrayList<NameSurferEntry> displayList;
	
	/* Constructor: 
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// Store entries to display in array list
		displayList = new ArrayList<NameSurferEntry>();
	}
	
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		displayList.clear();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		displayList.add(entry);
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		createBackground();
		reassemble();		
	}
	
	/* Draw vertical bars, horizontal bars, 
	 * and labels for all decades */
	private void createBackground() {
		double width = getWidth();
		double height = getHeight();
		// Initial position of x-coordinate
		double x = 0;
		int decade = START_DECADE;
		// Add upper and lower horizontal bars
		GLine upper_horizon = new GLine(0, GRAPH_MARGIN_SIZE, width, GRAPH_MARGIN_SIZE);
		GLine lower_horizon = new GLine(0, height-GRAPH_MARGIN_SIZE, width, height-GRAPH_MARGIN_SIZE);
		add(upper_horizon);
		add(lower_horizon);
		// Add vertical bars and decade labels for all decades
		for(int i=0; i<NDECADES; i++) {
			String decadesLabel = Integer.toString(decade);
			GLine verticle_bar = new GLine(x, 0, x, height);
			GLabel label = new GLabel(decadesLabel, x, height);
			add(verticle_bar);
			add(label);
			x += getWidth()/NDECADES;
			decade += 10;
		}
		
	}
	
	/* Plot according to entries in present list */
	private void reassemble(){
		for(int i=0; i<displayList.size(); i++) {
			Color color = colorChoose(i);
			NameSurferEntry entry = displayList.get(i);
			plotEntry(i, entry, color);
		}
	}
	
	/* Plot a single entry: adding lines and labels */
	private void plotEntry(int n, NameSurferEntry entry, Color color){
		String name = entry.getName();
		int lastRank = entry.getRank(0); // rank at the start decade
		// interval distance along x-axle between two consecutive points
		double x_interval = getWidth()/NDECADES;
		// x-coordinate of first point
		double lastX = 0;
		// calculate y-coordinate of first point
		double lastY, presentX, presentY;
		
		String lastLabel, presentLabel;
		// If entry ranks 0, it actually means it's out of top 1000
		if ( lastRank == 0 ) {
			lastY = getHeight() - GRAPH_MARGIN_SIZE;
			lastLabel = "*";
		} else {
			 lastY = GRAPH_MARGIN_SIZE 
			 	  + lastRank * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
			 lastLabel = Integer.toString(lastRank);
		}
		GObject firstObject = featureChoose(n);
		firstObject.setColor(color);
		add(firstObject, lastX, lastY);

		// add first label
		GLabel firstLabel = new GLabel(name + " " + lastLabel);
		firstLabel.setColor(color);
		add(firstLabel, lastX, lastY);
		// draw lines and labels
		for (int i=1; i<NDECADES; i++){
			int presentRank = entry.getRank(i);
			presentX = lastX + x_interval;
			if( presentRank == 0 ) {
				presentY = getHeight() - GRAPH_MARGIN_SIZE; 
				presentLabel = "*";
			}else{
				presentY = GRAPH_MARGIN_SIZE
				 + presentRank * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
				presentLabel = Integer.toString(presentRank);
			}
			// Add features at each decade, 
			// indicating rank of that decade 
			GObject object = featureChoose(n);
			object.setColor(color);
			add(object, presentX, presentY);
			// Add line between ranks of  two consecutive decades
			GLine line = new GLine(lastX, lastY, presentX, presentY);
			line.setColor(color);
			add(line);
			// Add label at each point, displaying the name and its rank
			GLabel label = new GLabel(name + " " + presentLabel);
			label.setColor(color);
			// if the name were increasing in popularity, 
			// display the label below the point; otherwise, above the point
			if( presentY < lastY){
				add(label, presentX, label.getAscent() + presentY);
			} else{
				add(label, presentX, presentY);
			}
			lastX = presentX;
			lastY = presentY;
		}
	}
	
	private Color colorChoose(int i){
		switch (i%4) {
		case 1: return Color.RED;
		case 2: return Color.BLUE;
		case 3: return Color.MAGENTA;
		default: return Color.BLACK;
		}
	}
	
	private GObject featureChoose(int i){
		GRect rect = new GRect(5, 5);
		GOval oval = new GOval(5, 5);
		GPolygon triangle = new GPolygon();
		triangle.addVertex(-5,0);
		triangle.addVertex(5, 0);
		triangle.addVertex(0, 8);
		GPolygon diamond = new GPolygon();
		diamond.addVertex(-5, 0);
		diamond.addVertex(0, 5);
		diamond.addVertex(5, 0);
		diamond.addVertex(0, -5);
		
		switch (i%4) {
		case 0:  return oval;	
		case 1:  return rect; 
		case 2:  return diamond;
		default: return triangle;
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
