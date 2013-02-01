
package com.thedemgel.mail.data;

import java.util.Date;


public class Message {
	private String body;
	private Date date;
	private long id;
	private String sender;
	private boolean read;
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String value) {
		this.body = value;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date value) {
		this.date = value;
	}
	
	public long getID() {
		return id;
	}
	
	public void setID(long value) {
		this.id = value;
	}
	
	public void setSender(String value) {
		this.sender = value;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setRead(boolean value) {
		this.read = value;
	}
	
	public boolean getRead() {
		return read;
	}
}
