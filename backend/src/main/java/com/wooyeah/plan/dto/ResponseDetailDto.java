package com.wooyeah.plan.dto;

import java.util.List;

public class ResponseDetailDto {
	
	private String uuid;
	private int id;
	private String writer;
	private String title;
	private String startDate;
	private String endDate;
	private int totalBudget;
	private List<ResponseDetailScheduleDto> schedules;
	private String planProfile;
	
	public ResponseDetailDto() {}
	
	public ResponseDetailDto(ResponseDetailPlanDto planDetail, List<ResponseDetailScheduleDto> schedules,
			int totalBudget, String imagePath) {
		this.uuid = planDetail.getUuid();
		this.id = planDetail.getId();
		this.writer = planDetail.getWriter();
		this.title = planDetail.getTitle();
		this.startDate = planDetail.getStart_date();
		this.endDate = planDetail.getEnd_date();
		this.totalBudget = totalBudget;
		this.schedules = schedules;
		this.planProfile = imagePath;

	}
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public int getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(int totalBudget) {
		this.totalBudget = totalBudget;
	}

	public List<ResponseDetailScheduleDto> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<ResponseDetailScheduleDto> schedules) {
		this.schedules = schedules;
	}
	public String getPlanProfile() {
		return planProfile;
	}
	public void setPlanProfile(String planProfile) {
		this.planProfile = planProfile;
	}

	@Override
	public String toString() {
		return "ResponseDetailDto [uuid=" + uuid + ", id=" + id + ", writer=" + writer + ", title=" + title
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", schedules=" + schedules + ", planProfile="
				+ planProfile + "]";
	}	
}
