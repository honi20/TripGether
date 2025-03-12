package com.wooyeah.notice.dto;

public class ResponseDetailMatchingDto {
	
	private String uuid;
	private String title;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "ResponseDetailMatchingDto [uuid=" + uuid + ", title=" + title + "]";
	}
	
}
