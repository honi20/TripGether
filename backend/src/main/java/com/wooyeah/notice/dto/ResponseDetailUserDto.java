package com.wooyeah.notice.dto;

public class ResponseDetailUserDto {
	private String name;
	private String gender;
	private String birth_year; // select 문에서 조심
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}
	@Override
	public String toString() {
		return "ResponseDetailUserDto [name=" + name + ", gender=" + gender + ", birth_year=" + birth_year + "]";
	}
	
	
}
