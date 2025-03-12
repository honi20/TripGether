package com.wooyeah.matching.dto.request;

import java.util.List;

public class RequestSearchDto {
	private String keyword;
	private int maxPeriod;
	private int maxBudget;
	private String sortType;
	private String sortCriteria;
	private String sortOrder;
	private int maxPersonCnt;
	private List<String> theme;
	private List<Integer> themeId;
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getMaxPeriod() {
		return maxPeriod;
	}
	public void setMaxPeriod(int maxPeriod) {
		this.maxPeriod = maxPeriod;
	}
	public int getMaxBudget() {
		return maxBudget;
	}
	public void setMaxBudget(int maxBudget) {
		this.maxBudget = maxBudget;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortCriteria() {
		return sortCriteria;
	}
	public void setSortCriteria(String sortCriteria) {
		this.sortCriteria = sortCriteria;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getMaxPersonCnt() {
		return maxPersonCnt;
	}
	public void setMaxPersonCnt(int maxPersonCnt) {
		this.maxPersonCnt = maxPersonCnt;
	}
	public List<String> getTheme() {
		return theme;
	}
	public void setTheme(List<String> theme) {
		this.theme = theme;
	}
	public List<Integer> getThemeId() {
		return themeId;
	}
	public void setThemeId(List<Integer> themeId) {
		this.themeId = themeId;
	}
	@Override
	public String toString() {
		return "RequestSearchDto [keyword=" + keyword + ", maxPeriod=" + maxPeriod + ", maxBudget=" + maxBudget
				+ ", sortType=" + sortType + ", sortCriteria=" + sortCriteria + ", sortOrder=" + sortOrder
				+ ", maxPersonCnt=" + maxPersonCnt + ", theme=" + theme + ", themeId=" + themeId + "]";
	}
		
}
