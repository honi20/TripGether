package com.wooyeah.diary.dto;

public class FileInfoDto {
	private int id;		// 이미지가 저장된 데이터의 식별자 번호
	private String saveFolder;
	private String originalFile;
	private String saveFile;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSaveFolder() {
		return saveFolder;
	}
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
	public String getOriginalFile() {
		return originalFile;
	}
	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	@Override
	public String toString() {
		return "FileInfoDto [id=" + id + ", saveFolder=" + saveFolder + ", originalFile="
				+ originalFile + ", saveFile=" + saveFile + "]";
	}
	
}
