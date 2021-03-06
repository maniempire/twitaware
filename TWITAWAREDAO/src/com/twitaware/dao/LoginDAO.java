package com.twitaware.dao;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.twitaware.dao.DataAccessObject;
import com.twitaware.dto.LoginDTO;
import com.twitaware.util.ShaUtil;




public class LoginDAO extends DataAccessObject{
	
	
	Connection sqlCon = null;
	Statement sqlStmt = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;
	
	

public LoginDTO isValidUser(LoginDTO loginDTO){
		
		String sqlQuery = null;

		String loginStatus = null;
		
		try	{
			

		sqlQuery = "select * from users  where user_email_id='"+loginDTO.getEmailId()+"' and user_password = '" +loginDTO.getPassword() +"'";

	
		loginDTO = new LoginDTO();
			
			sqlCon = getSQLConnection(); 
			
			sqlStmt =sqlCon.createStatement();
			
			resultSet = sqlStmt.executeQuery(sqlQuery);
			
			if(resultSet.next()){
							
				loginstatus(resultSet.getInt(1));
				loginStatus = "valid";
				
				loginDTO.setUserId(resultSet.getInt(1));
				loginDTO.setValidUser(loginStatus);
				
						
			}else{
				
				loginStatus = "error.pim.invaliduser";
				
				loginDTO.setValidUser(loginStatus);
				
			}
			
		}catch (Exception e){
			System.out.println(e);
		}finally{
			
			closeSQLConnection(sqlCon, sqlStmt, preparedStatement, resultSet);
		}
		
		return loginDTO;
	}



private void loginstatus(int userId) {
	
	Connection sqlCon = null;
	Statement sqlStmt = null;
	PreparedStatement preparedStatement = null;

	sqlCon = getSQLConnection();
	try {
		
	sqlStmt =sqlCon.createStatement();
		
	preparedStatement=sqlCon.prepareStatement("UPDATE users SET user_status='active' WHERE user_id="+userId+"");

	preparedStatement.executeUpdate();

	}catch (SQLException e) {
		
		e.printStackTrace();
	}
	closeSQLConnection(sqlCon, sqlStmt, preparedStatement,null);
	
	
}



public boolean logoutUser(int userId) {
	
	Connection sqlCon = null;
	Statement sqlStmt = null;
	PreparedStatement preparedStatement = null;
	String sqlQuery = null;
	boolean result = false;

	sqlCon = getSQLConnection();
	try {
		
	sqlStmt =sqlCon.createStatement();
		
	preparedStatement=sqlCon.prepareStatement("UPDATE users SET user_status='inactive' WHERE user_id="+userId+"");


	preparedStatement.executeUpdate();

	result=true;
	

	}catch (SQLException e) {
		
		e.printStackTrace();
	}
	closeSQLConnection(sqlCon, sqlStmt, preparedStatement,null);
	
	return result;
}
}