package com.wooyeah.matching.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.wooyeah.plan.dto.ResponseDetailDto;

// 동행 매칭 게시글 상세보기 시 전달되는 최종 dto
public class ResponseMatchingDetailDto {
	private String matchingUuid;
	private String title;
	private String writerId;
	private String writerName;
	private String writerGender;
	private int writerBirth;
	private String region;
	private LocalDate startDate;
	private LocalDate endDate;
	private int recruitPersonCnt;
	private int currentPersonCnt;
	private String description;
	private int totalBudget;
	private String planUuid;
	private ResponseThemeDto themes;
	
	public String getMatchingUuid() {
		return matchingUuid;
	}
	public void setMatchingUuid(String matchingUuid) {
		this.matchingUuid = matchingUuid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getWriterGender() {
		return writerGender;
	}
	public void setWriterGender(String writerGender) {
		this.writerGender = writerGender;
	}
	public int getWriterBirth() {
		return writerBirth;
	}
	public void setWriterBirth(int writerBirth) {
		this.writerBirth = writerBirth;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public int getRecruitPersonCnt() {
		return recruitPersonCnt;
	}
	public void setRecruitPersonCnt(int recruitPersonCnt) {
		this.recruitPersonCnt = recruitPersonCnt;
	}
	public int getCurrentPersonCnt() {
		return currentPersonCnt;
	}
	public void setCurrentPersonCnt(int currentPersonCnt) {
		this.currentPersonCnt = currentPersonCnt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(int totalBudget) {
		this.totalBudget = totalBudget;
	}
	public String getPlanUuid() {
		return planUuid;
	}
	public void setPlanUuid(String planUuid) {
		this.planUuid = planUuid;
	}
	public ResponseThemeDto getThemes() {
		return themes;
	}
	public void setThemes(ResponseThemeDto themes) {
		this.themes = themes;
	}
	
}