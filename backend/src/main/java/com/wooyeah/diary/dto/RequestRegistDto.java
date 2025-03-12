package com.wooyeah.diary.dto;

import java.util.List;

public class RequestRegistDto {
	private int id;
	private String content;
	private String matchingUuid;
	private String location;
	private String publicStatus;
	private String travelDate;
	private List<FileInfoDto> images;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMatchingUuid() {
		return matchingUuid;
	}
	public void setMatchingUuid(String matchingUuid) {
		this.matchingUuid = matchingUuid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPublicStatus() {
		return publicStatus;
	}
	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public List<FileInfoDto> getImages() {
		return images;
	}
	public void setImages(List<FileInfoDto> images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "RequestRegistDto [id=" + id + ", content=" + content + ", matchingUuid=" + matchingUuid + ", location="
				+ location + ", publicStatus=" + publicStatus + ", images=" + images + "]";
	}
	
}
