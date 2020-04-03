package com.capgemini.librarymanagementsystemjdbc.dao;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystemjdbc.dto.Book;
import com.capgemini.librarymanagementsystemjdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystemjdbc.dto.BorrowedBooks;
import com.capgemini.librarymanagementsystemjdbc.dto.User;

public interface UserDAO {
	boolean register(User user);
	User login(String email,String password);
	boolean addBook(Book book);
	boolean removeBook(int bId);
	boolean updateBook(Book book);
	boolean issueBook(int bId,int uId);
	boolean request(int uId, int bId);
	List<BorrowedBooks> borrowedBook(int uId);
	LinkedList<Book> searchBookById(int bId);
	LinkedList<Book> searchBookByTitle(String bookName);
	LinkedList<Book> searchBookByAuthor(String author);
	LinkedList<Book> getBooksInfo();
	boolean returnBook(int bId,int uId);
	LinkedList<BookIssueDetails> bookHistoryDetails(int uId);
	
}
