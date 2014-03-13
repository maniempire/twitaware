package com.twitaware.bl;

import com.twitaware.dao.ProfileDAO;
import com.twitaware.dto.ProfileDTO;

public class ProfileBL {
	
	
	public ProfileDTO insertUserProfile(ProfileDTO profileDTO){
		
		boolean userProfile=false;
		ProfileDAO profileDAO = new ProfileDAO();
		
		if(profileDTO != null){
			
			profileDTO = profileDAO.insertUserProfile(profileDTO);
		}
		
		return profileDTO;
	}

	public ProfileDTO getUserProfile(ProfileDTO profileDTO) {
		
		ProfileDAO profileDAO = new ProfileDAO();
		profileDTO=profileDAO.getuserProfile(profileDTO);
		
		return profileDTO;
	}
	
public ProfileDTO updateProfile(ProfileDTO profileDTO) {
		
		ProfileDAO profileDAO = new ProfileDAO();
		profileDTO=profileDAO.updateProfile(profileDTO);
		
		return profileDTO;
	}

	
}
