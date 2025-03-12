package com.wooyeah.matching.dto.request;

import com.wooyeah.matching.dto.ThemeInfoDto;

public class RequestModifyDto {
	private String description;
	private ThemeInfoDto themes;
	
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
}
