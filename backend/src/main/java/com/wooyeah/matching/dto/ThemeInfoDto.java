package com.wooyeah.matching.dto;

// 동행매칭 게시글 등록 시 요청되는 테마 정보
public class ThemeInfoDto {
	private int id;
	private boolean forEnergetic;
	private boolean forLeisurely;
	private boolean forNatural;
	private boolean forUrban;
	private boolean forHealing;
	private boolean forShopping;
	private boolean forMorning;
	private boolean forNightOwl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isForEnergetic() {
		return forEnergetic;
	}
	public void setForEnergetic(boolean forEnergetic) {
		this.forEnergetic = forEnergetic;
	}
	public boolean isForLeisurely() {
		return forLeisurely;
	}
	public void setForLeisurely(boolean forLeisurely) {
		this.forLeisurely = forLeisurely;
	}
	public boolean isForNatural() {
		return forNatural;
	}
	public void setForNatural(boolean forNatural) {
		this.forNatural = forNatural;
	}
	public boolean isForUrban() {
		return forUrban;
	}
	public void setForUrban(boolean forUrban) {
		this.forUrban = forUrban;
	}
	public boolean isForHealing() {
		return forHealing;
	}
	public void setForHealing(boolean forHealing) {
		this.forHealing = forHealing;
	}
	public boolean isForShopping() {
		return forShopping;
	}
	public void setForShopping(boolean forShopping) {
		this.forShopping = forShopping;
	}
	public boolean isForMorning() {
		return forMorning;
	}
	public void setForMorning(boolean forMorning) {
		this.forMorning = forMorning;
	}
	public boolean isForNightOwl() {
		return forNightOwl;
	}
	public void setForNightOwl(boolean forNightOwl) {
		this.forNightOwl = forNightOwl;
	}
	@Override
	public String toString() {
		return "ThemeInfoDto [id=" + id + ", forEnergetic=" + forEnergetic + ", forLeisurely=" + forLeisurely
				+ ", forNatural=" + forNatural + ", forUrban=" + forUrban + ", forHealing=" + forHealing
				+ ", forShopping=" + forShopping + ", forMorning=" + forMorning + ", forNightOwl=" + forNightOwl + "]";
	}
}
