package com.revature.repos;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public class UserRepo {

	private SessionFactory factory;
	// use this logger later for debugging if needed. REMOVE in production, or
	// implement AOP logging.
	Logger log = Logger.getLogger(UserRepo.class);

	@Autowired
	public UserRepo(SessionFactory factory) {
		this.factory = factory;
	}

	public List<User> getAll() {
		Session s = factory.getCurrentSession();
		return s.createQuery("from Users", User.class).getResultList();

	}

	public User getByCredentials(String username, String password) {
		Session s = factory.getCurrentSession();

		s.beginTransaction();

		User u = null;
		log.info("\n Retrieving user by credentials. ");

		// Potential cause for sequel injection? Quick check for most common SQL
		// injection
		// as in "; drop table users"
		if (username.contains(";"))
			u = null;
		Query userQuery = s.createQuery("from Users u where u.username = :usrnm ", User.class);
		userQuery.setParameter("usrnm", username);

		// getSingleResult() has the potential to throw a number of exceptions.
		try {
			u = (User) userQuery.getSingleResult();
		} catch (NoResultException nre) {
			log.info("No User found with these credentials: UN: " + username + " PW: " + password);
		} catch (Exception e) {
			log.info("Exception thrown in getByCredentials when invoked with these credentials: UN: " + username
					+ " PW: " + password);
		}
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
		User user = s.get(User.class, updatedUser.getUser_id());
		if (user == null)
			return null;
		else
			user = updatedUser;
		return user;
	}

	public boolean delete(int id) {
		Session s = factory.getCurrentSession();
		User user = s.get(User.class, id);
		if (user == null)
			return false;
		s.delete(user);
		return true;
	}
}
