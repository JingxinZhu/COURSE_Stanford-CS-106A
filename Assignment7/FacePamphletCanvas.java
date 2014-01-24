/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;


public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		message = new GLabel("");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message.setFont(MESSAGE_FONT);
		message.setLabel(msg);
		add(message,(getWidth() - message.getWidth())/2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		String user = profile.getName();
		// Display profile's name
		GLabel nameLabel = new GLabel(user);
		nameLabel.setColor(Color.BLUE);
		nameLabel.setFont(PROFILE_NAME_FONT);
		add(nameLabel, LEFT_MARGIN, TOP_MARGIN + nameLabel.getAscent());
	
		// Display profile's image
		GImage image = profile.getImage();
		double yImage = TOP_MARGIN + nameLabel.getAscent() + IMAGE_MARGIN;
		if ( image != null ) {
			image.scale(IMAGE_WIDTH/image.getWidth(),IMAGE_HEIGHT/image.getHeight());
			add(image, LEFT_MARGIN, yImage);
		} else {
			GRect imageRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel imageLabel = new GLabel("No image");
			imageLabel.setFont(PROFILE_IMAGE_FONT);
			double xLabel = LEFT_MARGIN 
							+ (IMAGE_WIDTH - imageLabel.getWidth()) / 2;
			double yLabel = yImage 
							+ (IMAGE_HEIGHT + imageLabel.getAscent()) / 2;
			add(imageRect, LEFT_MARGIN, yImage);
			add(imageLabel, xLabel, yLabel);	
		}

		
		// Display profile's status
		GLabel statusLabel = new GLabel("");
		String statusStr = profile.getStatus();
		if (statusStr.equals("")) {
			statusLabel.setLabel("No current status");
		} else {
			statusLabel.setLabel(user + " is " + statusStr);
		}
		statusLabel.setFont(PROFILE_STATUS_FONT);
		double yStatus = STATUS_MARGIN + yImage 
						+ IMAGE_HEIGHT + statusLabel.getAscent();
		add(statusLabel, LEFT_MARGIN, yStatus);
		
		// Display profile's friends
		double middle = getWidth() / 2;
		GLabel friendsLabel = new GLabel("Friends:");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsLabel, middle, yImage);

		double yFriends = yImage - friendsLabel.getAscent() 
							+ friendsLabel.getHeight(); 
		Iterator<String> friendsIt = profile.getFriends();
		while (friendsIt.hasNext()) {
			String friend = friendsIt.next();
			GLabel friendNames = new GLabel(friend);
			friendNames.setFont(PROFILE_FRIEND_FONT);
			add(friendNames, middle, yFriends + friendNames.getAscent());
			yFriends += friendNames.getHeight();
		}
		
//		// Display application message
//		GLabel application = new GLabel("Displaying " + user);
//		application.setFont(MESSAGE_FONT);
//		double xApplication = (getWidth() - application.getWidth()) / 2;
//		double yApplication = getHeight() - BOTTOM_MESSAGE_MARGIN;
//		add(application, xApplication, yApplication);
	}
	

	private GLabel message;
}
