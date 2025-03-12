package com.wooyeah.matching.dto.response;

import java.time.LocalDate;

// 메인 페이지에서 리스트에 보이는 데에 필요한 정보
public class ResponseListItemDto {
	private String matchingUuid;
	private String matchingTitle;
	private String matchingRegion;
	private int recruitPersonCnt;
	private int currentPersonCnt;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	
	public String getMatchingUuid() {
		return matchingUuid;
	}
	public void setMatchingUuid(String matchingUuid) {
		this.matchingUuid = matchingUuid;
	}
	public String getMatchingTitle() {
		return matchingTitle;
	}
	public void setMatchingTitle(String matchingTitle) {
		this.matchingTitle = matchingTitle;
	}
	public String getMatchingRegion() {
		return matchingRegion;
	}
	public void setMatchingRegion(String matchingRegion) {
		this.matchingRegion = matchingRegion;
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
	public LocalDate getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(LocalDate planStartDate) {
		this.planStartDate = planStartDate;
	}
	public LocalDate getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(LocalDate planEndDate) {
		this.planEndDate = planEndDate;
	}
	@Override
	public String toString() {
		return "ResponseListItemDto [matchingUuid=" + matchingUuid + ", matchingTitle=" + matchingTitle
				+ ", recruitPersonCnt=" + recruitPersonCnt + ", currentPersonCnt=" + currentPersonCnt
				+ ", planStartDate=" + planStartDate + ", planEndDate=" + planEndDate + "]";
	}

}