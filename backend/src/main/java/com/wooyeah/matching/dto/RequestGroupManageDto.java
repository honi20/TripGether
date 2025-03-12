package com.wooyeah.matching.dto;

public class RequestGroupManageDto {
	
	private String matchingUuid;
	private String userId;
	
	public String getMatchingUuid() {
		return matchingUuid;
	}
	public void setMatchingUuid(String matchingUuid) {
		this.matchingUuid = matchingUuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "RequestGroupManageDto [matchingUuid=" + matchingUuid + ", userId=" + userId + "]";
	}
	
}
