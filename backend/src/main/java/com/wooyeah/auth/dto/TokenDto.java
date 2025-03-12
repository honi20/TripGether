package com.wooyeah.auth.dto;

import java.util.Date;

public class TokenDto {
	private String uuid;
	private String token;
	private String hashedToken;
	private long issuedAt;
	private long expiration;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getHashedToken() {
		return hashedToken;
	}
	public void setHashedToken(String hashedToken) {
		this.hashedToken = hashedToken;
	}
	public long getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(long issuedAt) {
		this.issuedAt = issuedAt;
	}
	public long getExpiration() {
		return expiration;
	}
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	
	public Date getDateIssuedAt() {
		return new Date(this.issuedAt);
	}
	public Date getDateExpiration() {
		return new Date(this.expiration);
	}
}
