package com.wooyeah.notice.dto;

// 알림 리스트에 보여질 Dto
// uuid, type, title, created_at, read_status, processed_status
public class ResponseListDto {
	private String uuid;
	private String type;
	private String title;
	private String created_at;
	private int read_status;
	private int processed_status;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public int getRead_status() {
		return read_status;
	}
	public void setRead_status(int read_status) {
		this.read_status = read_status;
	}
	public int getProcessed_status() {
		return processed_status;
	}
	public void setProcessed_status(int processed_status) {
		this.processed_status = processed_status;
	}	

}
