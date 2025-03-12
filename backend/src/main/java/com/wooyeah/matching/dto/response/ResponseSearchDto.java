package com.wooyeah.matching.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResponseSearchDto {
	private String uuid;
	private String title;
	private String region;
	private int recruit_person_cnt;
	private int current_person_cnt;
	private String description;
	private int view_cnt;
	private LocalDateTime created_at;
	private String writer_id;
	private int plan_id;
	private LocalDate start_date;
	private LocalDate end_date;
	private String plan_img;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	public String getPlan_img() {
		return plan_img;
	}
	public void setPlan_img(String plan_img) {
		this.plan_img = plan_img;
	}

}
