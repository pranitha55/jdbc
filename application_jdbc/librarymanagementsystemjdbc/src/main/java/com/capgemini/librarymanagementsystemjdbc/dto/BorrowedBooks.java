package com.capgemini.librarymanagementsystemjdbc.dto;

import java.io.Serializable;

public class BorrowedBooks implements Serializable {
	private int uId;
	private int bId;
	private String email;
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
