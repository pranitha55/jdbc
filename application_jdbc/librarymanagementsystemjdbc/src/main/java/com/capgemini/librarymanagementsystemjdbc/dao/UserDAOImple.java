package com.capgemini.librarymanagementsystemjdbc.dao;

import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.capgemini.librarymanagementsystemjdbc.dto.Book;
import com.capgemini.librarymanagementsystemjdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystemjdbc.dto.BorrowedBooks;
import com.capgemini.librarymanagementsystemjdbc.dto.User;
import com.capgemini.librarymanagementsystemjdbc.exception.LMSException;

public class UserDAOImple implements UserDAO{
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Statement stmt = null;

	@Override
	public boolean register(User user) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("insert into users values(?,?,?,?,?,?,?)")){
				pstmt.setInt(1,user.getuId());
				pstmt.setString(2, user.getFirstName());
				pstmt.setString(3, user.getLastName());
				pstmt.setString(4, user.getEmail());
				pstmt.setString(5, user.getPassword());
				pstmt.setLong(6, user.getMobile());
				pstmt.setString(7, user.getRole());
				int count = pstmt.executeUpdate();
				if(user.getEmail().isEmpty() && count==0) {
					return false;
				} else {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User login(String email, String password) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from users where email=? and password=?");) {
				pstmt.setString(1,email);
				pstmt.setString(2,password);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					User bean = new User();
					bean.setuId(rs.getInt("uId"));
					bean.setFirstName(rs.getString("firstName"));
					bean.setLastName(rs.getString("lastName"));
					bean.setEmail(rs.getString("email"));
					bean.setPassword(rs.getString("password"));
					bean.setMobile(rs.getLong("mobile"));
					bean.setRole(rs.getString("role"));
					return bean;
				} else {
					return null;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addBook(Book book) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("insert into bookbean values(?,?,?,?,?)");) {
				pstmt.setInt(1, book.getBId());
				pstmt.setString(2, book.getBookName());
				pstmt.setString(3, book.getAuthor());
				pstmt.setString(4, book.getCategory());
				pstmt.setString(5, book.getPublisher());
				//pstmt.setInt(6, book.getCopies());
				int count = pstmt.executeUpdate();
				if(count!=0) {
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeBook(int bId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("delete from bookbean where bid=?");) {
				pstmt.setInt(1,bId);
				int count=pstmt.executeUpdate();
				if(count!=0) {
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateBook(Book book) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("update bookbean set bookname=? where bid=?");) {
				pstmt.setString(1,book.getBookName());
				pstmt.setInt(2,book.getBId());
				int count=pstmt.executeUpdate();
				if(count!=0) {
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean issueBook(int bId,int uId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from request_details where uid=? and bid=? and email=(select email from users where uid=?)")) {
				pstmt.setInt(1, uId);
				pstmt.setInt(2, bId);
				pstmt.setInt(3, uId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					try(PreparedStatement pstmt1 = conn.prepareStatement("insert into book_issue_details values(?,?,?,?)");){
						pstmt1.setInt(1, bId);
						pstmt1.setInt(2, uId);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
						Calendar cal = Calendar.getInstance();
						String issueDate = sdf.format(cal.getTime());
						pstmt1.setDate(3, java.sql.Date.valueOf(issueDate));
						cal.add(Calendar.DAY_OF_MONTH, 7);
						String returnDate = sdf.format(cal.getTime());
						pstmt1.setDate(4, java.sql.Date.valueOf(returnDate));
						int count=pstmt1.executeUpdate();
						if(count != 0) {	
							try(PreparedStatement pstmt2 = conn.prepareStatement("Insert into borrowed_books values(?,?,(select email from users where uid=?))")){
								pstmt2.setInt(1, uId);
								pstmt2.setInt(2, bId);
								pstmt2.setInt(3, uId);
								int isBorrowed = pstmt2.executeUpdate();
								if(isBorrowed != 0) {
									return true;
								}else {
									return false;
								}
							}
						} else {
							throw new LMSException("Book Not issued");
						}					
					}
				} else {
					throw new LMSException("The respective user have not placed any request");
				}			
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean request(int uId, int bId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select count(*) as uid from borrowed_books where uid=? and bid=? and email=(select email from users where uid=?)");) {
				pstmt.setInt(1, uId);
				pstmt.setInt(2, bId);
				pstmt.setInt(3, uId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					int isBookExists = rs.getInt("uId");
					if(isBookExists==0) {
						try(PreparedStatement pstmt1 = conn.prepareStatement("select count(*) as uid from book_issue_details where uid=?");) {
							pstmt1.setInt(1, uId);
							rs=pstmt1.executeQuery();
							if(rs.next()) {
								int noOfBooksBorrowed = rs.getInt("uId");
								if(noOfBooksBorrowed<3) {
									try(PreparedStatement pstmt2 = conn.prepareStatement("insert into request_details values(?,(select concat(firstname,'_',lastname) from users where uid=?)"
											+ ",?,(select bookname from bookbean where bid=?),(select email from users where uid=?))");){
										pstmt2.setInt(1,uId);
										pstmt2.setInt(2, uId);
										pstmt2.setInt(3, bId);
										pstmt2.setInt(4, bId);
										pstmt2.setInt(5, uId);
										int count = pstmt2.executeUpdate();
										if(count != 0) {
											return true;
										}else {
											return false;
										}
									}				 
								}else {
									throw new LMSException("no Of books limit has crossed");
								}
							}else {
								throw new LMSException("no of books limit has crossed");
							}		
						}				
					}else{
						throw new LMSException("You have already borrowed the requested book");
					}		
				}else {
					throw new LMSException("You have already borrowed the requested book");
				}			
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public LinkedList<Book> searchBookByTitle(String bookName) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from bookbean where bookname=?");) {
				pstmt.setString(1,bookName);
				rs=pstmt.executeQuery();
				LinkedList<Book> beans = new LinkedList<Book>();
				while(rs.next()) {
					Book bean = new Book();
					bean.setBId(rs.getInt("bId"));
					bean.setBookName(rs.getString("bookName"));
					bean.setAuthor(rs.getString("author"));
					bean.setCategory(rs.getString("category"));
					bean.setPublisher(rs.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<Book> searchBookByAuthor(String author) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from bookbean where author=?");) {
				pstmt.setString(1,author);
				rs=pstmt.executeQuery();
				LinkedList<Book> beans = new LinkedList<Book>();
				while(rs.next()) {
					Book bean = new Book();
					bean.setBId(rs.getInt("bId"));
					bean.setBookName(rs.getString("bookName"));
					bean.setAuthor(rs.getString("author"));
					bean.setCategory(rs.getString("category"));
					bean.setPublisher(rs.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<Book> getBooksInfo() {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					Statement stmt = (Statement)conn.createStatement();) {
				rs = stmt.executeQuery("select * from bookbean");
				LinkedList<Book> beans = new LinkedList<Book>();
				while(rs.next()) {
					Book bean = new Book();
					bean.setBId(rs.getInt("bId"));
					bean.setBookName(rs.getString("bookName"));
					bean.setAuthor(rs.getString("author"));
					bean.setCategory(rs.getString("category"));
					bean.setPublisher(rs.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean returnBook(int bId,int uId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("delete from book_issue_details where bid=? and uid=?");) {
				pstmt.setInt(1,bId);
				pstmt.setInt(2,uId);
				int count =  pstmt.executeUpdate();
				if(count != 0) {
					try(PreparedStatement pstmt1 = conn.prepareStatement("delete from borrowed_books where bid=? and uid=?");) {
						pstmt1.setInt(1, bId);
						pstmt1.setInt(2, uId);
						int isReturned = pstmt1.executeUpdate();
						if(isReturned != 0 ) {
							return true;
						}else {
							return false;
						}
					}
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public LinkedList<BookIssueDetails> bookHistoryDetails(int uId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select count(*) as uid from book_issue_details where uid=?");) {
				pstmt.setInt(1, uId);
				rs=pstmt.executeQuery();
				LinkedList<BookIssueDetails> beans = new LinkedList<BookIssueDetails>();
				while(rs.next()) {
					BookIssueDetails issueDetails = new BookIssueDetails();
					issueDetails.setUserId(rs.getInt("uId"));
					beans.add(issueDetails);
				} 
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BorrowedBooks> borrowedBook(int uId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from borrowed_books where uid=?");) {
				pstmt.setInt(1, uId);
				rs=pstmt.executeQuery();
				LinkedList<BorrowedBooks> beans = new LinkedList<BorrowedBooks>();
				while(rs.next()) {
					BorrowedBooks listOfbooksBorrowed = new BorrowedBooks();
					listOfbooksBorrowed.setuId(rs.getInt("uId"));
					listOfbooksBorrowed.setbId(rs.getInt("bId"));
					listOfbooksBorrowed.setEmail(rs.getString("email"));
					beans.add(listOfbooksBorrowed);
				} 
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<Book> searchBookById(int bId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from bookbean where bid=?");) {
				pstmt.setInt(1,bId);
				rs=pstmt.executeQuery();
				LinkedList<Book> beans = new LinkedList<Book>();
				while(rs.next()) {
					Book bean = new Book();
					bean.setBId(rs.getInt("bId"));
					bean.setBookName(rs.getString("bookName"));
					bean.setAuthor(rs.getString("author"));
					bean.setCategory(rs.getString("category"));
					bean.setPublisher(rs.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	}
	


