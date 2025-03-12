package com.wooyeah.user.entity;

import java.time.LocalDate;
import java.util.Date;

public class UserEntity {
	
	private String uuid;
	private String id;
	private String password;
	private String name;
	private String gender;
	private LocalDate birthday;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "UserEntity [uuid=" + uuid + ", id=" + id + ", password=" + password + ", name=" + name + ", gender="
				+ gender + ", birthday=" + birthday + "]";
	}
	
}
