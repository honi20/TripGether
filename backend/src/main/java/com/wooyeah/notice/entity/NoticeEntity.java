package com.wooyeah.notice.entity;

import java.util.Date;

// DB와 일치시키기
public class NoticeEntity {
	private String uuid;
	private String id;
	private String type;
	private String title;
	private String msg;
	private Date created_at;
	private int read_status;
	private int processed_status;
	private String sender_id;
	private String receiver_id;
	private String matching_id;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
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
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getMatching_id() {
		return matching_id;
	}
	public void setMatching_id(String matching_id) {
		this.matching_id = matching_id;
	}	
}
