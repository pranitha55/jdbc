package com.capgemini.librarymanagementsystemjdbc.factory;

import com.capgemini.librarymanagementsystemjdbc.dao.UserDAO;
import com.capgemini.librarymanagementsystemjdbc.dao.UserDAOImple;
import com.capgemini.librarymanagementsystemjdbc.service.UserService;
import com.capgemini.librarymanagementsystemjdbc.service.UserServiceImple;

public class LibraryFactory {

	
	public static UserDAO getUserDAO() {
		return new UserDAOImple();
	}
	public static UserService getUserService() {
		return new UserServiceImple();
	}
	
}
