package com.wooyeah.matching.entity;

public class ThemeEntity {
	private int id;
	private boolean for_energetic;
	private boolean for_leisurely;
	private boolean for_natural;
	private boolean for_urban;
	private boolean for_healing;
	private boolean for_shopping;
	private boolean for_morning;
	private boolean for_night_owl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isFor_energetic() {
		return for_energetic;
	}
	public void setFor_energetic(boolean for_energetic) {
		this.for_energetic = for_energetic;
	}
	public boolean isFor_leisurely() {
		return for_leisurely;
	}
	public void setFor_leisurely(boolean for_leisurely) {
		this.for_leisurely = for_leisurely;
	}
	public boolean isFor_natural() {
		return for_natural;
	}
	public void setFor_natural(boolean for_natural) {
		this.for_natural = for_natural;
	}
	public boolean isFor_urban() {
		return for_urban;
	}
	public void setFor_urban(boolean for_urban) {
		this.for_urban = for_urban;
	}
	public boolean isFor_healing() {
		return for_healing;
	}
	public void setFor_healing(boolean for_healing) {
		this.for_healing = for_healing;
	}
	public boolean isFor_shopping() {
		return for_shopping;
	}
	public void setFor_shopping(boolean for_shopping) {
		this.for_shopping = for_shopping;
	}
	public boolean isFor_morning() {
		return for_morning;
	}
	public void setFor_morning(boolean for_morning) {
		this.for_morning = for_morning;
	}
	public boolean isFor_night_owl() {
		return for_night_owl;
	}
	public void setFor_night_owl(boolean for_night_owl) {
		this.for_night_owl = for_night_owl;
	}
	@Override
	public String toString() {
		return "ThemeEntity [id=" + id + ", for_energetic=" + for_energetic + ", for_leisurely=" + for_leisurely
				+ ", for_natural=" + for_natural + ", for_urban=" + for_urban + ", for_healing=" + for_healing
				+ ", for_shopping=" + for_shopping + ", for_morning=" + for_morning + ", for_night_owl=" + for_night_owl
				+ "]";
	}
	
	
}
