/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		   nameField = new JTextField(TEXT_FIELD_SIZE);
		 statusField = new JTextField(TEXT_FIELD_SIZE);
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		 friendField = new JTextField(TEXT_FIELD_SIZE);
		   addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
		statusButton = new JButton("Change Status");
	   pictureButton = new JButton("Change Picture");
		friendButton = new JButton("Add friend");

		add(statusField, WEST);
		add(statusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureField, WEST);
		add(pictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendField, WEST);
		add(friendButton, WEST);	
		add(new JLabel("Name"), NORTH);
		add(nameField, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
		
		/* *********************** */
		/* Extension part I starts */
		 fileField = new JTextField(TEXT_FIELD_SIZE);    
		loadButton = new JButton("Load");
		saveButton = new JButton("Save");
		add(fileField, NORTH);
		add(loadButton, NORTH);
		add(saveButton, NORTH);
		/* Extension part I ends */
		/* ********************* */

		addActionListeners();
		statusField.addActionListener(this);
		pictureField.addActionListener(this);
		friendField.addActionListener(this);
		
		faceDB = new FacePamphletDatabase();
		
		canvas = new FacePamphletCanvas();
		add(canvas);
	
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    		Object obj = e.getSource();
    		
    		/* ************************ */
    		/* Extension Part II starts */ 	
    		
    		if (obj == loadButton) {
    			String file = fileField.getText();
			try {
				 faceDB.loadFile(file);
				 canvas.showMessage("File loaded");
			} catch (ErrorException ex) {		
				canvas.showMessage("Unable to open file " + file );
			}
		}
    		
    		if (obj == saveButton) {
    			String fileName = fileField.getText();
    			try {
					faceDB.saveFile(fileName);
					canvas.showMessage("Saved file " + fileName);
				} catch (ErrorException ex) {
					// TODO: handle exception
					throw new ErrorException(ex);
				}			
		}
    		
    		/* Extension Part II ends */
    		/* ********************** */
    		
      	// Add profile button
    		if (obj == addButton) {
			String name = nameField.getText();
			if (faceDB.containsProfile(name)) {
				currentProfile = faceDB.getProfile(name);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("A profile with the name " 
									+ name + " already exists");
			} else if ( !name.equals("")) {
				currentProfile = new FacePamphletProfile(name);
				faceDB.addProfile(currentProfile);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("New profile created");
			}	
    		}
    		
    		// Delete profile button
    		if (obj == deleteButton) {
			String name = nameField.getText();
			canvas.removeAll();
			if (faceDB.containsProfile(name)) {
				faceDB.deleteProfile(name);
				canvas.showMessage("Profile of " 
						+ name + " deleted");
				} else {
					canvas.showMessage("A profile with the name " 
										+ name + " does not exist");
				}
			currentProfile = null;
		}
    		
    		// Lookup profile button
    		if (obj == lookupButton) {
			String name = nameField.getText();
			if (faceDB.containsProfile(name)) {
				currentProfile = faceDB.getProfile(name);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Displaying " + name);
			} else {
				currentProfile = null;
				canvas.removeAll();
				canvas.showMessage("A profile with the name " 
									+ name + " does not exist");
			}
		}
    		
    		// Change status button
    		if (obj == statusField || obj == statusButton) {
    			String text = statusField.getText();
			if ( currentProfile != null ) {
				currentProfile.setStatus(text);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Status updated to " + text);
			} else {
				canvas.showMessage("Please select a profile to change status");
			}
    		}
    		
    		// Change picture button
    		if (obj == pictureField || obj == pictureButton) {
    			String filename = pictureField.getText();
			if (currentProfile != null) {
				GImage image = null;
				try {
					/* ************************* */
					/* Extension Part VII starts */
					currentProfile.setImageFile(filename);
					/*  Extension Part VII ends  */
					/* ************************* */
				  	image = new GImage(filename);
					currentProfile.setImage(image);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Picture updated");
				} catch (ErrorException ex) {
					canvas.showMessage("Unable to open image file: " + filename);
				}
			} else {
				canvas.showMessage("Please select a " +
						            "profile to change picture");
			}
    		}
    		
    		// Add friend button
    		if (obj == friendField || obj == friendButton) {
    			String text = friendField.getText();
			if (currentProfile == null) {
				canvas.showMessage("Please select a profile to add friend");
			} else if ( !faceDB.containsProfile(text) ) {
				canvas.showMessage( text + " does not exist");
			} else if ( text == currentProfile.getName()) {
				canvas.showMessage("Unable to add oneself as a friend");
			} else if (currentProfile.addFriend(text)) {
				faceDB.getProfile(text).addFriend(currentProfile.getName());
				canvas.displayProfile(currentProfile);
				canvas.showMessage(text + " added as a friend");
			} else {
				canvas.showMessage(currentProfile.getName()
								+ "already has " + text + " as a friend.");
			}
    		}
    
    }
    
    /* Private instance variables */
    private JTextField nameField;
    private JTextField statusField;
    private JTextField pictureField;
    private JTextField friendField;
    private JTextField fileField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton lookupButton;
    private JButton statusButton;
    private JButton pictureButton;
    private JButton friendButton;
    private JButton loadButton;
    private JButton saveButton;
	private FacePamphletProfile currentProfile;
    private FacePamphletDatabase faceDB;
    private FacePamphletCanvas canvas;


 

}
