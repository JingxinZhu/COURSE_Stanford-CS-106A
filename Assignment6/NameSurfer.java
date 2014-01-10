/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

// public class NameSurfer extends Program implements NameSurferConstants {
public class NameSurfer extends Program implements NameSurferConstants{

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the top of the window.
 */
	public void init() {
		// Add buttons
		nameField = new JTextField(MAX_COLUMNS);
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		add(new JLabel("Name"), NORTH);
		add(nameField, NORTH);
		add(graphButton, NORTH);
		add(clearButton, NORTH);
		// Add action listeners
		addActionListeners();
		nameField.addActionListener(this);
		// Create name database from file
		nameDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		// Add graph
		graph = new NameSurferGraph();
		add(graph);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == graphButton || obj == nameField) {
			// Find out the entry
			NameSurferEntry entry = 	nameDataBase.findEntry(nameField.getText());
			if(entry != null){
				// Add entry to graph's display list
				graph.addEntry(entry);
				// Update graph
				graph.update();
			}
		} else if (obj == clearButton) {
			// Delete all entries in display list
			graph.clear();
			graph.update();
		} 
	}

/* Private constants */
	private static final int MAX_COLUMNS = 25;
	
/* Private instance variables */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private NameSurferGraph graph;
	private NameSurferDataBase nameDataBase;
}
