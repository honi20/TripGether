package com.wooyeah.diary.dto;

import java.util.List;

public class ResponseDiaryItemDto {
	
	private String diaryUuid;
	private String writerId;
	private String writerName;
	private String writerProfile;
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
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "ResponseDiaryItemDto [diaryUuid=" + diaryUuid + ", images=" + images + "]";
	}
	
}
