package com.wooyeah.notice.dto;

import java.util.Date;

public class ResponseDetailDto {
	
	private String uuid;
    private String type;
    private String sender_id;
    private String userName;	// 발신자 정보
    private String userGender;
    private String userBirthYear;
    private String matchingTitle;
    private String msg;
    private String matchingUuid;
    private int matchingId;
    private int processed_status;
    
	public ResponseDetailDto(ResponseDetailNoticeDto noticeDetail, ResponseDetailUserDto userDetail, ResponseDetailMatchingDto matchingDetail) {
		this.uuid = noticeDetail.getUuid();
		this.type = noticeDetail.getType();
	    this.sender_id = noticeDetail.getSender_id();
	    this.userName = userDetail.getName();
	    this.userGender = userDetail.getGender();
	    this.userBirthYear = userDetail.getBirth_year();
	    this.matchingTitle = matchingDetail.getTitle();
	    this.msg = noticeDetail.getMsg();
	    this.matchingUuid = matchingDetail.getUuid();
	    this.matchingId = noticeDetail.getMatching_id();
	    this.processed_status = noticeDetail.getProcessed_status();
	}

	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
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

	public String getUserBirthYear() {
		return userBirthYear;
	}

	public void setUserBirthYear(String userBirthYear) {
		this.userBirthYear = userBirthYear;
	}

	public String getMatchingTitle() {
		return matchingTitle;
	}

	public void setMatchingTitle(String matchingTitle) {
		this.matchingTitle = matchingTitle;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMatchingUuid() {
		return matchingUuid;
	}

	public void setMatchingUuid(String matchingUuid) {
		this.matchingUuid = matchingUuid;
	}

	public int getMatchingId() {
		return matchingId;
	}

	public void setMatchingId(int matchingId) {
		this.matchingId = matchingId;
	}
	
	public int getProcessed_status() {
		return processed_status;
	}

	public void setProcessed_status(int processed_status) {
		this.processed_status = processed_status;
	}


	@Override
	public String toString() {
		return "ResponseDetailDto [uuid=" + uuid + ", userName=" + userName + ", matchingId=" + matchingId
				+ ", processed_status=" + processed_status + "]";
	}



    
	
    
}
