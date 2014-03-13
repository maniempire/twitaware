package com.twitaware.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




import com.twitaware.bl.LoginBL;

import com.twitaware.bl.ProfileBL;
import com.twitaware.bl.TwitterBL;
import com.twitaware.dto.LinkedInDTO;
import com.twitaware.dto.LoginDTO;
import com.twitaware.dto.PostDTO;
import com.twitaware.dto.ProfileDTO;
import com.twitaware.web.actionform.HomeActionForm;
import com.twitaware.web.actionform.LoginActionForm;
import com.twitaware.web.actionform.ProfileActionForm;

public class LoginAction extends Action{
	
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
	
		
		LoginBL loginBL = null;
		
		ProfileBL profileBL =null;
		
		ProfileDTO profileDTO = null;
		
		LoginActionForm loginActionForm = null;
		
		ProfileActionForm profileActionForm = new ProfileActionForm();

		HttpSession session = null;
//		PostBL postBL = null;
		PostDTO postDTO = null;
		
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();
		
		
		LoginDTO  loginDTO = new LoginDTO();
		
		String userId = null;
		String password = null;
		String validUser = null;
		
		String nextPage = null;
		String linkUrl = "";
			
		try{
			
			loginActionForm = (LoginActionForm)form;
			
			loginBL = new LoginBL();
			
			session = request.getSession(true);
			
			linkUrl = request.getParameter("link_url");
			
				
			
			loginDTO.setEmailId(loginActionForm.getEmailId());
		          	
			loginDTO.setPassword(loginActionForm.getPassword());
			
			loginDTO = loginBL.isValidUser(loginDTO);
			
			if(loginDTO != null){
				
				if(loginDTO.getValidUser().equals("valid")){
					
					List userTags;
					
					HomeActionForm homeActionForm = new HomeActionForm();
					profileDTO = new ProfileDTO();
					profileBL = new ProfileBL();
//					postBL = new PostBL();
					postDTO = new PostDTO();
					
					profileDTO.setUserId(loginDTO.getUserId());
					
					profileDTO=profileBL.getUserProfile(profileDTO);
					profileActionForm.setProfileDTO(profileDTO);
					
					session.setAttribute("USERID", profileDTO.getUserId());
					session.setAttribute("FIRSTNAME", profileDTO.getFirstName());
					
					postDTO.setUserId(loginDTO.getUserId());
					
//					userTags = postBL.getTagList(postDTO);
//					if(userTags.equals("")){
//						homeActionForm.setUserTags(userTags);
//					}
					//userTags.add("ddddd");
					
					initTwitter(String.valueOf(profileDTO.getUserId()), session);
					
//					initFaceBook(String.valueOf(profileDTO.getUserId()), session);
					
//					initLinkedIn(userId, session, request);
					
					nextPage="SUCCESS";
					System.out.println("Login Success");
				}else{
					
					errors.add("PIMERROR", new ActionError("errors.pim.invaliduser"));
					
					nextPage="Failure";
				}

				
			}else{
				
				errors.add("PIMERROR", new ActionError(
				"errors.pim.internalerror"));
				nextPage="Failure";
			
			}
			//}
		}catch(Exception e){
		
			e.printStackTrace();
			errors.add("PIMERROR", new ActionError(
			"errors.pim.internalerror"));
			
			nextPage="EXCEPTION";
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);			
		} else {
		}
		
		forward = mapping.findForward(nextPage);
		
		return forward;
	}
	
	public void initTwitter(String userId, HttpSession session){
	
	TwitterBL twitterBL = new TwitterBL();
	
	boolean twitterConnected = false;
	
	
//	twitterConnected = twitterBL.isTwitterConnected(userId);
//	
//	if(twitterConnected){
//		
//		session.setAttribute("TWITTERCONNECTED", "CONNECTED");
//		
//	}else{
//		
		String twitterAuthURL = null;
		
		twitterAuthURL = twitterBL.initAuthUrl();
		
		session.setAttribute("TWITTERAUTHURL", twitterAuthURL);
		
		session.setAttribute("TWITTERCONNECTED", "NOTCONNECTED");
	//	}
		
	}
	
	
	
	
	
}
