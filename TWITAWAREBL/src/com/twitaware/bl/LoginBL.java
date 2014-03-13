package com.twitaware.bl;


//import com.sociorespo.dto.ProfileDTO;
import com.twitaware.dao.LoginDAO;
import com.twitaware.dto.LoginDTO;


public class LoginBL {

	
	public LoginDTO isValidUser(LoginDTO loginDTO) {
		
		
	
		LoginDAO loginDAO = new LoginDAO();
		loginDTO = loginDAO.isValidUser(loginDTO);
		
		
		return loginDTO;
	}

	public boolean logoutUser(int userId) {
		
		LoginDAO loginDAO = new LoginDAO();
		boolean result = loginDAO.logoutUser(userId);
		
		return result;
	}
}
