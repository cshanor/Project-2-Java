package com.revature.repos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.revature.models.User;

@Repository
public class UserRepo {

	private SessionFactory factory;

	@Autowired
	public UserRepo(SessionFactory factory) {
		this.factory = factory;
	}
	
	public List<User> getAll(){
		Session s = factory.getCurrentSession();
		return s.createQuery("from Users", User.class).getResultList();
		
	}
	
	
	public User getByCredentials(String username, String password) {
		Session s = factory.getCurrentSession();
		User u = s.get(User.class, username);
		if(!(password == u.getPassword())) return null;
		return u; 
		
	}
	
	public User getById(int id) {
		Session s = factory.getCurrentSession();
		return s.get(User.class, id);
	}
	
	public User add(User newUser) {
		Session s = factory.getCurrentSession();
		s.save(newUser);
		return newUser;
	}
	
	public User update(User updatedUser) {
		Session s = factory.getCurrentSession();
		User user = s.get(User.class,  updatedUser.getUser_id());
		if(user == null) return null;
		else user = updatedUser;
		return user; 
	}
	
	public boolean delete(int id) {
		Session s = factory.getCurrentSession();
		User user = s.get(User.class, id);
		if(user == null) return false;
		s.delete(user);
		return true; 
	}
}
