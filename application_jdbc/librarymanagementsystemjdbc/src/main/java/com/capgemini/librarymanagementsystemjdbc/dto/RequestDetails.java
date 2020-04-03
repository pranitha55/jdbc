package com.capgemini.librarymanagementsystemjdbc.dto;

import java.io.Serializable;

public class RequestDetails implements Serializable {
	private int uId;
	private String fullName;
	private int bId;
	private String bookName;
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
