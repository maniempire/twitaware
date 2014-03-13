package com.twitaware.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import twitter4j.http.AccessToken;




import com.twitaware.bl.ProfileBL;
import com.twitaware.bl.TwitterBL;
import com.twitaware.dto.LinkedInDTO;
import com.twitaware.dto.ProfileDTO;
import com.twitaware.web.actionform.ProfileActionForm;


public class ProfileAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws IOException{
		
		String nextPage =null;
		
		String linkUrl="";
		HttpSession session = null;
		
		ActionErrors errors = new ActionErrors();
		
		ProfileDTO profileDTO = null;
		ProfileBL profileBL = null;
		String userId = null;
		try{
		
		session = request.getSession(true);
		
		userId = session.getAttribute("USERID").toString();
		
		ProfileActionForm profileActionForm = (ProfileActionForm)form;
		
		//initSocialMedia(userId,session, profileActionForm, request);
		
//		faceBookProfile(userId,session, profileActionForm);
		
		twitterProfile(userId, session, profileActionForm);
		
//		linkedInProfile(userId, session, profileActionForm, request);
		
		profileBL = new ProfileBL();
		
		String userSessionId = session.getAttribute("USERID").toString();
		//int userId = ;
		profileActionForm.getUpdate();
		
		profileDTO = new ProfileDTO();
	
		
		profileDTO.setUserId(Integer.parseInt(userSessionId));
		
		linkUrl = request.getParameter("link_url");
		
		
		
		if (linkUrl != null) {
			if (linkUrl.equals("update")) {
			//if(profileActionForm.getUpdate()=="update"){
				profileDTO.setFirstName(profileActionForm.getFirstName());
				profileDTO.setLastName(profileActionForm.getLastName());
				profileDTO.setMailId(profileActionForm.getMailId());
				if(profileActionForm.getConfirmPassword()!=null && profileActionForm.getConfirmPassword()!=""){
					profileDTO.setPassword(profileActionForm.getConfirmPassword());
				}else{
					profileDTO.setPassword(profileActionForm.getPassword());
				}
				profileDTO.setGender(profileActionForm.getGender());
				profileDTO.setPhoneNum(profileActionForm.getPhoneNum());
				profileDTO.setVoterId(profileActionForm.getVoterId());
				profileDTO.setDob(profileActionForm.getDob());
				
				profileDTO = profileBL.updateProfile(profileDTO);
				session.setAttribute("FIRSTNAME", profileDTO.getFirstName());
				nextPage = "SUCCESS";
				//session.invalidate();
			}else if(linkUrl.equals("edit")){
				profileDTO=profileBL.getUserProfile(profileDTO);
				profileActionForm.setConfirmPassword(null);
				profileActionForm.setNewPassword(null);
				nextPage = "EDITPROFILE";
			}
			
		//else{
		//profileDTO=profileBL.getUserProfile(profileDTO);
		
		//profileActionForm.setProfileDTO(profileDTO);
		
		//profileDTO = faceBookBL.getFaceBookProfile(profileDTO);
		
		//profileActionForm.setFaceBookProfileDTO(profileDTO);
		
		//LinkedInBL linkedInBl = new LinkedInBL();
		
		//String authURL = null;
		//String baseURL = null;
		
		//baseURL ="http://" + request.getServerName() + ":" + request.getServerPort() +  request.getContextPath();
//		profileDTO.setBaseURL(baseURL);
//		profileDTO.setUserId(Integer.parseInt(userId));
//		LinkedInDTO linkedInDTO = linkedInBl.getLinkedInAuthURL(profileDTO);
//		
//
//		session.setAttribute("LINKEDINREQUESTTOKEN", linkedInDTO.getRequestToken());
		
		//profileActionForm.setLinkedInAuthURL(linkedInDTO.getAuthUrl());

		//authURL = linkedInBl.getLinkedInAuthURL(baseURL);
	
		//profileActionForm.setLinkedInAuthURL(linkedInDTO.getAuthUrl());

		
		errors.add("PIMERROR", new ActionError("errors.pim.profileupdated.success"));
		
		//}
		}else{
			profileDTO=profileBL.getUserProfile(profileDTO);
			nextPage="SUCCESS";
		}
		profileActionForm.setFirstName(profileDTO.getFirstName());
		profileActionForm.setLastName(profileDTO.getLastName());
		profileActionForm.setMailId(profileDTO.getMailId());
		profileActionForm.setPassword(profileDTO.getPassword());
		profileActionForm.setGender(profileDTO.getGender());
		profileActionForm.setPhoneNum(profileDTO.getPhoneNum());
		profileActionForm.setDob(profileDTO.getDob());
		profileActionForm.setVoterId(profileDTO.getVoterId());
		
		}catch(Exception e){
			e.printStackTrace();
			errors.add("PIMERROR", new ActionError(
			"errors.pim.internalerror"));
			
			nextPage="EXCEPTION";
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);			
		} 
		
		
		
		
		return mapping.findForward(nextPage);
		
	}
	
	
	
public void twitterProfile(String userId, HttpSession session, ProfileActionForm profileActionForm) {
		
	String twitterConnected = null;
	TwitterBL twitterBL = new TwitterBL();
	
	twitterConnected = (String)session.getAttribute("TWITTERCONNECTED");
	
	if(twitterConnected.equals("CONNECTED")){
		
		AccessToken accessToken = null;
		
		ProfileDTO twitterProfileDTO = null;

		
		accessToken = (AccessToken)session.getAttribute("TWITTERACCESSTOKEN");
		
		twitterProfileDTO = twitterBL.getUserTwitterProfileDetails(accessToken);
		
		
		//twitterProfileDTO = twitterBL.getUserTwitterProfileDetails(userId);
		profileActionForm.setTwitterProfileDTO(twitterProfileDTO);

		
	}else{
		String twitterAuthURL = null;
		
		twitterAuthURL = (String)session.getAttribute("TWITTERAUTHURL");
		
		profileActionForm.setTwitterAuthURL(twitterAuthURL);
		
	}
	
	
	
	}








	
}
