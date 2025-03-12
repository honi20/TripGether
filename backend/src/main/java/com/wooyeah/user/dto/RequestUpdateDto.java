package com.wooyeah.user.dto;

public class RequestUpdateDto {

	private String userPassword;
	private FileInfoDto userProfile;
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public FileInfoDto getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(FileInfoDto userProfile) {
		this.userProfile = userProfile;
	}
}
