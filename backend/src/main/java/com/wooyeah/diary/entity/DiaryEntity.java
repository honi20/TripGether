package com.wooyeah.diary.entity;

import java.time.LocalDateTime;

public class DiaryEntity {
	private String uuid;
	private int id;
	private String writer_id;
	private int matching_id;
	private String content;
	private String location;
	private String public_status;
	private String travel_date;
	private LocalDateTime created_at;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public int getMatching_id() {
		return matching_id;
	}
	public void setMatching_id(int matching_id) {
		this.matching_id = matching_id;
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
	public String getPublic_status() {
		return public_status;
	}
	public void setPublic_status(String public_status) {
		this.public_status = public_status;
	}
	public String getTravel_date() {
		return travel_date;
	}
	public void setTravel_date(String travel_date) {
		this.travel_date = travel_date;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	
}
