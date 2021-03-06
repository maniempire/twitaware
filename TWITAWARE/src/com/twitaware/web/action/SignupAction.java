package com.twitaware.web.action;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



import com.twitaware.bl.ProfileBL;
import com.twitaware.dto.ProfileDTO;
import com.twitaware.web.actionform.ProfileActionForm;
import com.twitaware.web.actionform.SignupActionForm;

import java.text.SimpleDateFormat;

 
public class SignupAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
	
		String nextPage = null;
		
		String linkUrl = "";
		
		boolean userProfile = false;
		
		ProfileBL profileBL = null;
		
		HttpSession session = null;
		
		SignupActionForm signupActionForm = null;
		session = request.getSession(true);
		
		ProfileActionForm profileActionForm = new ProfileActionForm();
		
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();
		
		ProfileDTO profileDTO = null;
		
		
		linkUrl = request.getParameter("link_url");
		
		if (linkUrl != null) {
			if (linkUrl.equals("cancel")) {
				
				nextPage = "FAILURE";
				
			}
			
		}
		if (linkUrl != null) {
			if (linkUrl.equals("save")) {
				try{
			
					signupActionForm = (SignupActionForm)form;
					profileBL = new ProfileBL();
					profileDTO = new ProfileDTO();
					
					
					profileDTO.setUserId(signupActionForm.getUserId());
					profileDTO.setFirstName(signupActionForm.getFirstName());
					profileDTO.setLastName(signupActionForm.getLastName());
					profileDTO.setPassword(signupActionForm.getPassword());
					profileDTO.setGender(signupActionForm.getGender());
					profileDTO.setPhoneNum(signupActionForm.getPhoneNum());
					profileDTO.setMailId(signupActionForm.getMailId());
					profileDTO.setDob(signupActionForm.getDob());
					profileDTO.setVoterId(signupActionForm.getVoterId());
			
					
					profileDTO = profileBL.insertUserProfile(profileDTO);
					errors.add("PIMERROR", new ActionError(
					"errors.user.add.success"));
					nextPage="SUCCESS";
//					if(profileDTO != null){
//			
//						//if(profileDTO.getUserId()!=null){
//				
//						profileDTO.setUserId(profileDTO.getUserId());
//				
//						//profileDTO=profileBL.getUserProfile(profileDTO);
//				
//						//profileActionForm.setProfileDTO(profileDTO);
//				
//						session.setAttribute("PROFILE", profileDTO);
//						session.setAttribute("USERID", profileDTO.getUserId());
//						session.setAttribute("FIRSTNAME", profileDTO.getFirstName());
//				
//						nextPage="SUCCESS";
//				
//					}else{
//						nextPage="FAILURE";
//					}
//			
			
			}catch(Exception e){
		
			e.printStackTrace();
			errors.add("PIMERROR", new ActionError(
			"errors.pim.internalerror"));
			
			nextPage="EXCEPTION";
		}
			}
			}
	
		if (!errors.isEmpty()) {
			saveErrors(request, errors);			
		} else {
		}
			forward = mapping.findForward(nextPage);
		
		return forward;
	}
	


}
