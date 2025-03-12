package com.wooyeah.matching.entity;

import java.time.LocalDateTime;

public class MatchingEntity {
	private String uuid;
	private int id;
	private String title;
	private String region;
	private int recruit_person_cnt;
	private int current_person_cnt;
	private String description;
	private int view_cnt;
	private LocalDateTime created_at;
	private String writer_id;
	private int plan_id;
	private int theme_id;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getRecruit_person_cnt() {
		return recruit_person_cnt;
	}
	public void setRecruit_person_cnt(int recruit_person_cnt) {
		this.recruit_person_cnt = recruit_person_cnt;
	}
	public int getCurrent_person_cnt() {
		return current_person_cnt;
	}
	public void setCurrent_person_cnt(int current_person_cnt) {
		this.current_person_cnt = current_person_cnt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public int getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(int theme_id) {
		this.theme_id = theme_id;
	}
	@Override
	public String toString() {
		return "MatchingEntity [uuid=" + uuid + ", id=" + id + ", title=" + title + ", region=" + region
				+ ", recruit_person_cnt=" + recruit_person_cnt + ", current_person_cnt=" + current_person_cnt
				+ ", description=" + description + ", view_cnt=" + view_cnt + ", created_at=" + created_at
				+ ", writer_id=" + writer_id + ", plan_id=" + plan_id + ", theme_id=" + theme_id + "]";
	}
	
	
}
