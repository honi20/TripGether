package com.wooyeah.plan.dto;

import java.time.LocalDate;
import java.util.Date;

public class PlanListItemDto {
	private String uuid;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean outOfDate;
	private String planImage;
	
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
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public boolean isOutOfDate() {
		return outOfDate;
	}
	public void setOutOfDate(boolean outOfDate) {
		this.outOfDate = outOfDate;
	}
	public String getPlanImage() {
		return planImage;
	}
	public void setPlanImage(String planImage) {
		this.planImage = planImage;
	}
	@Override
	public String toString() {
		return "PlanListItemDto [uuid=" + uuid + ", title=" + title + ", startDate=" + startDate + ", endDate="
				+ endDate + ", outOfDate=" + outOfDate + "]";
	}

	
}
