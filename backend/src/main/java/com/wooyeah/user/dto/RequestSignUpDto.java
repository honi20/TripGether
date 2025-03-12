package com.wooyeah.user.dto;

import java.util.Date;

public class RequestSignUpDto {
	private String userId;
	private String userPassword;
	private String userName;
	private String userGender;
	private Date userBirthday;
	private FileInfoDto userProfile;
	
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
	public Date getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
	public FileInfoDto getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(FileInfoDto userProfile) {
		this.userProfile = userProfile;
	}
	@Override
	public String toString() {
		return "RequestSignUpDto [userId=" + userId + ", userPassword=" + userPassword + ", userName=" + userName
				+ ", userGender=" + userGender + ", userBirthday=" + userBirthday + ", userProfile=" + userProfile
				+ "]";
	}
	
}
