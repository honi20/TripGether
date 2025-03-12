package com.wooyeah.diary.entity;

public class DiaryImgEntity {
	private int id;
	private String save_folder;
	private String original_file;
	private String save_file;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSave_folder() {
		return save_folder;
	}
	public void setSave_folder(String save_folder) {
		this.save_folder = save_folder;
	}
	public String getOriginal_file() {
		return original_file;
	}
	public void setOriginal_file(String original_file) {
		this.original_file = original_file;
	}
	public String getSave_file() {
		return save_file;
	}
	public void setSave_file(String save_file) {
		this.save_file = save_file;
	}
	
}
