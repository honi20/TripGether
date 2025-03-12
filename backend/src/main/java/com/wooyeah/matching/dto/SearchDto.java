package com.wooyeah.matching.dto;

import java.util.List;

public class SearchDto {
	private String keyword;
	private int maxPeriod;
	private int maxBudget;
	private String sortCriteria;
	private String sortOrder;
	private int maxPersonCnt;
	private List<Integer> themeIdList;
	
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
	public List<Integer> getThemeIdList() {
		return themeIdList;
	}
	public void setThemeIdList(List<Integer> themeIdList) {
		this.themeIdList = themeIdList;
	}
	@Override
	public String toString() {
		return "SearchDto [keyword=" + keyword + ", maxPeriod=" + maxPeriod + ", maxBudget=" + maxBudget
				+ ", sortCriteria=" + sortCriteria + ", sortOrder=" + sortOrder + ", maxPersonCnt=" + maxPersonCnt
				+ ", themeIdList=" + themeIdList + "]";
	}

}
