package com.capgemini.librarymanagementsystemjdbc.controller;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementsystemjdbc.dto.Book;
import com.capgemini.librarymanagementsystemjdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystemjdbc.dto.BorrowedBooks;
import com.capgemini.librarymanagementsystemjdbc.dto.User;
import com.capgemini.librarymanagementsystemjdbc.exception.LMSException;
import com.capgemini.librarymanagementsystemjdbc.factory.LibraryFactory;
import com.capgemini.librarymanagementsystemjdbc.service.UserService;
import com.capgemini.librarymanagementsystemjdbc.validation.Validation;



public class LMSController {

	public static void main(String[] args) {
		doReg();
	}
	public static void doReg() {
		boolean flag = false;

		int regId = 0; 
		String regFirstName = null; 
		String regLastName = null; 
		long regMobile = 0;
		String regEmail = null;
		String regPassword = null;

		boolean loginStatus = true;
		Validation validation = new Validation();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press 1 to Register");
		System.out.println("Press 2 to Login");
		System.out.println("Press 3 to EXIT");
		do {
			UserService service1= LibraryFactory.getUserService();
			int choice = scanner.nextInt();
			switch(choice) {
			case 1:
				do {
					try {
						System.out.println("Enter ID :");
						regId = scanner.nextInt();
						validation.validatedId(regId);
						flag = true;
					} catch (InputMismatchException e) {
						flag = false;
						System.err.println("Id should contains only digits");
					} catch (LMSException e) {
						flag = false;
						System.err.println(e.getMessage());
					}
				} while (!flag);

				do {
					try {
						System.out.println("Enter First Name :");
						regFirstName = scanner.next();
						validation.validatedName(regFirstName);
						flag = true;
					} catch (InputMismatchException e) {
						flag = false;
						System.err.println("Name should contains only Alphabates");
					} catch (LMSException e) {
						flag = false;
						System.err.println(e.getMessage());
					}
				} while (!flag);
				do {
					try {
						System.out.println("Enter Last Name :");
						regLastName = scanner.next();
						validation.validatedName(regLastName);
						flag = true;
					} catch (InputMismatchException e) {
						flag = false;
						System.err.println("Name should contains only Alphabates");
					} catch (LMSException e) {
						flag = false;
						System.err.println(e.getMessage());
					}
				} while (!flag);

				do {
					try {
						System.out.println("Enter Email :");
						regEmail = scanner.next();
						validation.validatedEmail(regEmail);
						flag = true;
					} catch (InputMismatchException e) {
						flag = false;
						System.err.println("Email should be proper ");
					} catch (LMSException e) {
						flag = false;
						System.err.println(e.getMessage());
					}
				} while (!flag);

				do {
					try {
						System.out.println("Enter Password :");
						regPassword = scanner.next();
						validation.validatedPassword(regPassword);
						flag = true;
					} catch (InputMismatchException e) {
						flag = false;
						System.err.println("Enter correct Password ");
					} catch (LMSException e) {
						flag = false;
						System.err.println(e.getMessage());
					}
				} while (!flag);

				do {
					try {
						System.out.println("Enter Mobile :");
						regMobile = scanner.nextLong();
						validation.validatedMobile(regMobile);
						flag = true;
					} catch (InputMismatchException e) {
						flag = false;
						System.err.println("Mobile Number  should contains only numbers");
					} catch (LMSException e) {
						flag = false;
						System.err.println(e.getMessage());
					}
				} while (!flag);
				System.out.println("Enter the role");
				String regRole = scanner.next();
				User ai = new User();
				ai.setuId(regId);
				ai.setFirstName(regFirstName);
				ai.setLastName(regLastName);
				ai.setEmail(regEmail);
				ai.setPassword(regPassword);
				ai.setMobile(regMobile);
				ai.setRole(regRole);
				boolean check=service1.register(ai);
				if(check) {
					System.out.println("Registered");
				}else {
					System.out.println("Already user is registered");
				}
				break;
			case 2:
				System.out.println("enter email");
				String email=scanner.next();
				System.out.println("enter password");
				String password=scanner.next();
				try {
					User loginInfo=service1.login(email, password);
					if(loginInfo.getEmail().equals(email) && loginInfo.getPassword().equals(password)) {
						System.out.println("Logged In");
					}
					if(loginInfo.getRole().equals("admin")) {
						do {
							System.out.println("-----------------------------------------------");
							System.out.println("Press 1 to add book");
							System.out.println("Press 2 to remove book");
							System.out.println("Press 3 to issue book");
							System.out.println("Press 4 to Search the Book by Author");
							System.out.println("Press 5 to Search the Book by Title");
							System.out.println("Press 6 to Get the Book Information");
							System.out.println("Press 7 to Search the book by Id");
							System.out.println("Press 8 to update the book");
							System.out.println("Press 9 to check student book history");
							System.out.println("Press 10 to logout");

							int choice1 = scanner.nextInt();
							switch (choice1) {
							case 1:
								System.out.println("enter id");
								int addId=scanner.nextInt();
								System.out.println("enter bookname");
								String addName=scanner.next();
								System.out.println("enter authorname");
								String addAuth=scanner.next();
								System.out.println("enter category");
								String addCategory=scanner.next();
								System.out.println("enter publisher");
								String addPublisher=scanner.next();
								/*
								 * System.out.println("enter no of copies"); int addCopies = scanner.nextInt();
								 */
								Book bi =new Book();
								bi.setBId(addId);
								bi.setBookName(addName);
								bi.setAuthor(addAuth);
								bi.setCategory(addCategory);
								bi.setPublisher(addPublisher);
								//bi.setCopies(addCopies);
								boolean check2=service1.addBook(bi);
								if(check2) {
									System.out.println("-----------------------------------------------");
									System.out.println("Added Book");
								}else {
									System.out.println("-----------------------------------------------");
									System.out.println("Book not added");
								}
								break;	
							case 2:
								System.out.println("enter id");
								int removeId=scanner.nextInt();
								boolean check3=service1.removeBook(removeId);
								if(check3) {
									System.out.println("-----------------------------------------------");
									System.out.println("Removed Book");
								}else {
									System.out.println("-----------------------------------------------");
									System.out.println("Book not removed");
								}
								break;

							case 3:
								System.out.println("enter Book Id");
								int issueId=scanner.nextInt();
								System.out.println("Enter User Id");
								int userId = scanner.nextInt();
								boolean check4=service1.issueBook(issueId,userId);
								if(check4) {
									System.out.println("-----------------------------------------------");
									System.out.println("Book Issued");
								}else {
									System.out.println("-----------------------------------------------");
									System.out.println("Book not issued");
								}
								break;
							case 4:
								System.out.println("Search the book by the Author Name:");
								String author = scanner.next();

								List<Book> bookauthor = service1.searchBookByAuthor(author);
								for (Book bookBean : bookauthor) {

									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are available written by this author");
									}
								}
								break;
							case 5:
								System.out.println("  Search the book by the Book_Title :");
								String btitle = scanner.next();

								List<Book> booktitle = service1.searchBookByTitle(btitle);
								for (Book bookBean : booktitle) {	
									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are available with this title.");
									}
								}
								break;

							case 6:
								LinkedList<Book> info = service1.getBooksInfo();
								for (Book bookBean : info) {

									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("Books info is not present");
									}
								}
								break;
							case 7:
								System.out.println("  Search the book by the Book_ID :");
								String book_Id = scanner.next();

								List<Book> bId = service1.searchBookByTitle(book_Id);
								for (Book bookBean : bId) {	
									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are available with this title.");
									}
								}
								break;
							case 8:
								System.out.println("Enter the updated id :");
								int bid= scanner.nextInt();
								System.out.println("Enter bookName to be updtaed");
								String updatedBookName =scanner.next();
								Book bean2 = new Book();
								bean2.setBId(bid);
								bean2.setBookName(updatedBookName);
								boolean updated = service1.updateBook(bean2);
								if (updated) {
									System.out.println("-----------------------------------------------");
									System.out.println("Book is updated");
								} else {
									System.out.println("-----------------------------------------------");
									System.out.println("Book is not updated");
								}
								break;

							case 9:
								System.out.println("Enter the User Id");
								int user_Id = scanner.nextInt();
	
								List<BookIssueDetails> uid = service1.bookHistoryDetails(user_Id);
								for (BookIssueDetails issueDetails : uid) {
									if(issueDetails != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("No of books Borrowed :"+issueDetails.getUserId());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("Respective user hasn't borrowed any books");
									}
								}
								break;

							case 10:
								doReg();

							default:
								System.out.println("-----------------------------------------------");
								System.out.println("Invalid Choice");
								break;
							}
						}while(true);
					}
					else if(loginInfo.getRole().equals("student")) {
						do {
							System.out.println("-----------------------------------------------");
							System.out.println("Press 1 to request book");
							System.out.println("Press 2 to view the books borrowed");
							System.out.println("Press 3 to search book by author");
							System.out.println("Press 4 to search book by title");
							System.out.println("Press 5 to search book by Id");
							System.out.println("Press 6 to get books info");
							System.out.println("Press 7 to return book");
							System.out.println("Press 8 to main");


							int choice2 = scanner.nextInt();
							switch (choice2) {
							case 1:
								System.out.println("Enter the Book Id:");
								int reqBookId= scanner.nextInt();
								System.out.println("Enter the user Id:");
								int reqUserId = scanner.nextInt();
								boolean requested = service1.request(reqUserId,reqBookId);
								if (requested != false) {
									System.out.println("-----------------------------------------------");
									System.out.println("Book is Requested");
								} else {
									System.out.println("-----------------------------------------------");
									System.out.println("Book is not Requested");
								}	
								break;
								
							case 2:
								System.out.println("Enter the user Id");
								int user_Id = scanner.nextInt();
								List<BorrowedBooks> borrowedBookList = service1.borrowedBook(user_Id);
								for (BorrowedBooks bookBean : borrowedBookList) {

									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("User_Id is-->"+bookBean.getuId());
										System.out.println("Book_Id is-->"+bookBean.getbId());
										System.out.println("Email Id is-->"+bookBean.getEmail());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are borrowed by the user");
									}
								}
								break;
																 
							case 3:
								System.out.println("Search the book by the Author Name :");
								String author = scanner.next();
								
								List<Book> bookauthor = service1.searchBookByAuthor(author);
								for (Book bookBean : bookauthor) {

									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are available written by this author");
									}
								}
								break;
							case 4:
								System.out.println("Search the book by the Book Title :");
								String btitle = scanner.next();
								
								List<Book> booktitle = service1.searchBookByAuthor(btitle);
								for (Book bookBean : booktitle) {	
									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are available with this title.");
									}
								}
								break;
							case 5:
								System.out.println("  Search the book by the Book_ID :");
								String book_Id = scanner.next();

								List<Book> bId = service1.searchBookByTitle(book_Id);
								for (Book bookBean : bId) {	
									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("No books are available with this title.");
									}
								}
								break;
							case 6:
								LinkedList<Book> info = service1.getBooksInfo();
								for (Book bookBean : info) {

									if (bookBean != null) {
										System.out.println("-----------------------------------------------");
										System.out.println("Book_Id is-->"+bookBean.getBId());
										System.out.println("Book_Name is-->"+bookBean.getBookName());
										System.out.println("Book_Author is-->"+bookBean.getAuthor());
										System.out.println("Book_PublisherName is-->"+bookBean.getPublisher());
										System.out.println("Book_Category is-->"+bookBean.getCategory());
										//System.out.println("No_Of_Copies are-->"+bookBean.getCopies());
									} else {
										System.out.println("-----------------------------------------------");
										System.out.println("Books info is not presernt");
									}
								}
								break;
							case 7:
								System.out.println("Enter the Book id to return :");
								int returnId= scanner.nextInt();
								System.out.println("Enter userId");
								int userId = scanner.nextInt();				
								boolean returned = service1.returnBook(returnId,userId);
								if (returned != false) {
									System.out.println("-----------------------------------------------");
									System.out.println("Book is Returned");
								} else {
									System.out.println("-----------------------------------------------");
									System.out.println("Book is not Returned");
								}	
								break;

							case 8:
								doReg();
							default:
								break;
							}
						}while(true);
					}
				}catch(Exception e) {
					System.out.println("Invalid Credentials");
					System.out.println("Try logging in again,Press 2 to login");
				}
				break;
			default:
				System.out.println("Exit");
				loginStatus = false;
			}
		}while(loginStatus);
	}

}
