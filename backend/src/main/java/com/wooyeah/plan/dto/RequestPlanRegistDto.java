package com.wooyeah.plan.dto;

import java.util.List;

import com.wooyeah.plan.dto.FileInfoDto;

public class RequestPlanRegistDto {
	
	private String writer;
	private String title;
	private String startDate;
	private String endDate;
	private List<Schedule> schedules;
	private FileInfoDto planProfile;
	
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
	public List<Schedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	public FileInfoDto getPlanProfile() {
		return planProfile;
	}
	public void setPlanProfile(FileInfoDto planProfile) {
		this.planProfile = planProfile;
	}
	@Override
	public String toString() {
		return "RequestPlanRegistDto [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", schedules=" + schedules + "]";
	}
	
	
	

}
