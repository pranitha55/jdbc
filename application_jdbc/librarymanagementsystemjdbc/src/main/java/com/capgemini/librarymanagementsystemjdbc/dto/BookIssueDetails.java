package com.capgemini.librarymanagementsystemjdbc.dto;

import java.io.Serializable;
import java.util.Date;

public class BookIssueDetails  implements Serializable {
	private int bookId;
	private int userId;
	private Date issueDate;
	private Date returnDate;
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}	

}
