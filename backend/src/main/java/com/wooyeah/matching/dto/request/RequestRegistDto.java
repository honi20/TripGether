package com.wooyeah.matching.dto.request;

import com.wooyeah.matching.dto.ThemeInfoDto;

// 동행매칭 게시글 등록 시 요청 dto
public class RequestRegistDto {
	private int id;
	private String planUuid;
    private String title;
    private String region;
    private int recruitPersonCnt;
    private String description;
	private ThemeInfoDto themes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlanUuid() {
		return planUuid;
	}
	public void setPlanUuid(String planUuid) {
		this.planUuid = planUuid;
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
	public int getRecruitPersonCnt() {
		return recruitPersonCnt;
	}
	public void setRecruitPersonCnt(int recruitPersonCnt) {
		this.recruitPersonCnt = recruitPersonCnt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ThemeInfoDto getThemes() {
		return themes;
	}
	public void setThemes(ThemeInfoDto themes) {
		this.themes = themes;
	}
	@Override
	public String toString() {
		return "RequestRegistDto [id=" + id + ", planUuid=" + planUuid + ", title=" + title + ", region=" + region
				+ ", recruitPersonCnt=" + recruitPersonCnt + ", description=" + description + ", themes=" + themes
				+ "]";
	}
	
}
