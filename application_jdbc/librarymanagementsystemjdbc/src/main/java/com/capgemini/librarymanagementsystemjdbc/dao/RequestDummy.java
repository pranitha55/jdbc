package com.capgemini.librarymanagementsystemjdbc.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.capgemini.librarymanagementsystemjdbc.exception.LMSException;

public class RequestDummy {
	public boolean request(int uId,int bId) {
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
											+ "(select email from users where uid=?),?,(select bookname from bookbean where bid=?))");){
										pstmt2.setInt(1,uId);
										pstmt2.setInt(2, uId);
										pstmt2.setInt(3, uId);
										pstmt2.setInt(4, bId);
										pstmt2.setInt(5, bId);
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

}
