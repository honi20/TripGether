package com.wooyeah.user.entity;

public class ProfileEntity {
	private int id;
	private String user_id;
	private String save_folder;
	private String original_file;
	private String save_file;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
