package com.wooyeah.user.dto;

import java.time.LocalDate;
import java.util.Date;

public class UserDto {
	
	private String userId;
	private String userPassword;
	private String userName;
	private String userGender;
	private LocalDate userBirthday;
	private String userProfile;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public LocalDate getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(LocalDate userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	
}
