package com.wooyeah.plan.dto;

import java.util.List;

public class ResponseDetailScheduleDto {
	
	private int id; // 스케쥴 dto에서 스케쥴 id 받아와서 그 안에 매칭되는 배열 넣어놓기
	private int day;
	private int idx;
	private List<Place> places;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
}
