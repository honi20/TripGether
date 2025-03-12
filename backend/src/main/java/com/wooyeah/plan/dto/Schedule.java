package com.wooyeah.plan.dto;

import java.util.List;

public class Schedule {
	private int day;
	private int idx;
	private List<Place> places;
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	
	@Override
	public String toString() {
		return "schedule [day=" + day + ", idx=" + idx + ", places=" + places + "]";
	}
	
	
}
