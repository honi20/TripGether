package com.wooyeah.diary.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseDiaryDetailDto {
	private String diaryUuid;
	private String writerId;
	private String writerName;
	private String writerProfile;
	private String content;
	private String location;
	private String publicStatus;
	private String travelDate;
	private LocalDateTime created_at;
	private List<String> images;
	
	public String getDiaryUuid() {
		return diaryUuid;
	}
	public void setDiaryUuid(String diaryUuid) {
		this.diaryUuid = diaryUuid;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getWriterProfile() {
		return writerProfile;
	}
	public void setWriterProfile(String writerProfile) {
		this.writerProfile = writerProfile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
}
