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
		
		JTextField emptyField = new JTextField(9);
		emptyField.setVisible(false);
		add(emptyField,NORTH);
		
		add(new JLabel("Name"), NORTH);
		add(nameField, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
		
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
									+ name + "does not exist");
		    	}
	    	}
    		
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
    		
    		if (obj == pictureField || obj == pictureButton) {
    			String filename = pictureField.getText();
			    if ( currentProfile != null ) {
				    GImage image = null;
				try {
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
    		
    		if (obj == friendField || obj == friendButton) {
    			String text = friendField.getText();
			    if (currentProfile == null) {
				    canvas.showMessage("Please select a profile to add friend");
		    	} else if ( !faceDB.containsProfile(text) ) {
			    	canvas.showMessage( text + " does not exist");
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
    private JButton addButton;
    private JButton deleteButton;
    private JButton lookupButton;
    private JButton statusButton;
    private JButton pictureButton;
    private JButton friendButton;
	private FacePamphletProfile currentProfile;
    private FacePamphletDatabase faceDB;
    private FacePamphletCanvas canvas;


 

}
