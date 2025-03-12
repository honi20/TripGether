package com.wooyeah.plan.dto;

public class Place {
	private int place_order;
	private String time;
	private String name;
	private String description;
	private int budget;
	private double latitude;
	private double longitude;
	

	public int getPlace_order() {
		return place_order;
	}
	public void setPlace_order(int place_order) {
		this.place_order = place_order;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
