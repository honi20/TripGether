package com.wooyeah.notice.dto;

public class ResponseDetailNoticeDto {
	
	private String uuid;
	private String type;
	private String sender_id;
	private String msg;
	private int matching_id;
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
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getMatching_id() {
		return matching_id;
	}
	public void setMatching_id(int matching_id) {
		this.matching_id = matching_id;
	}
	public int getProcessed_status() {
		return processed_status;
	}
	public void setProcessed_status(int processed_status) {
		this.processed_status = processed_status;
	}
	@Override
	public String toString() {
		return "ResponseDetailNoticeDto [type=" + type + ", sender_id=" + sender_id + ", msg=" + msg + ", matching_id="
				+ matching_id + "]";
	}
		
	

}
