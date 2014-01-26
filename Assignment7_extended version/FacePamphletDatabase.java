/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;
import java.io.*;

import acm.graphics.GImage;
import acm.util.ErrorException;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {

	}
	
	/* *********************** */
	/* Extension Part III starts */
	public void loadFile(String file) {
		try {
			socialMap.clear();
			BufferedReader rd 
				= new BufferedReader(new FileReader(file));
			int nProfile = Integer.parseInt(rd.readLine());
			
			for (int i = 0; i < nProfile; i++) {	
				
				// Create new profile
				String name = rd.readLine();
				FacePamphletProfile profile = new FacePamphletProfile(name);

				// Set image if image file exits
				String image = rd.readLine();
				profile.setImageFile(image);
				if (image.length() != 0) {
					profile.setImage(new GImage(image));
				}
				
				// Set status for this profile
				String status = rd.readLine();
				profile.setStatus(status);

				// Add friends for this profile
				while (true) {
					String friend = rd.readLine();
					if (friend.length() == 0) break;
					profile.addFriend(friend);
				} 
				socialMap.put(name, profile);
			}
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
	public void saveFile(String fileName) {
		try {
			File file = new File(fileName);
			
			// Create a new file if file does not exist
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter wt = new BufferedWriter(fileWriter);
			
			// Write the number of total profiles in current database
			String num = Integer.toString(socialMap.size());			
			wt.write(num);
			wt.newLine();
			
			// Write profiles one by one
			Iterator<String> userIt = socialMap.keySet().iterator();
			while (userIt.hasNext()) {
				String user = userIt.next();
				FacePamphletProfile profile = socialMap.get(user);
				// Write user name
				wt.write(user);
				wt.newLine();
				// Write image file name
				String imageFile = profile.getImageFile();
				if (imageFile != null) {
					wt.write(profile.getImageFile());
				}
				wt.newLine();
				System.out.print(profile.getImageFile());
				// Write status
				wt.write(profile.getStatus());
				wt.newLine();
				// Write friends
				Iterator<String> friendsIt = profile.getFriends();
				while (friendsIt.hasNext()) {
					wt.write(friendsIt.next());
					wt.newLine();
				}
				// Write an empty line denoting the end of this profile
				wt.newLine();	
			}
			wt.close();
		} catch (IOException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}	
	}
	
	/* Extension Part III ends */
	/* ********************* */
		
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		String user = profile.getName();
		socialMap.put(user, profile);
	}

	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		return socialMap.get(name);
	}
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (socialMap.containsKey(name)) {
			socialMap.remove(name);
			Iterator<String> it = socialMap.keySet().iterator();
			while (it.hasNext()) {
				String user = it.next();
				socialMap.get(user).removeFriend(name);
			}	
		}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return socialMap.containsKey(name);
	}
	
	/* Private instance variables */
	private HashMap<String, FacePamphletProfile> socialMap
				= new HashMap<String, FacePamphletProfile>();

}
