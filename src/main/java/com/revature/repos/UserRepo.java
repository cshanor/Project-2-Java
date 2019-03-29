package com.revature.repos;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Tag;
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

	public List<User> getFriendsByUser_Id(int user_id) {
		Session s = factory.getCurrentSession();
		List<User> friends = null;

		try {
			// Target the junction table, where user_id = id of current user.
			// This has to be targeted differently, because the JunctionTable is defined
			// Within the user class, so we cannot say "from User".
			// My reference for this code: https://www.objectdb.com/java/jpa/query/parameter
			Query friendsQuery = s.createNativeQuery("Select * from USER_FRIENDS where user_id = ?", User.class);

			// If this does not work, use a standard sql "?" with position of "0"
			// as we did when using JDBC. The "?1" is
			// an Ordinal Parameter (?index) which comes from JPA.

			friends = friendsQuery.setParameter(0, user_id).getResultList();

		} catch (Exception e) {
			log.info("Exception thrown in getFriends() in UserRepo while trying to get friends by User");
			e.printStackTrace();
		}

		return friends;
	}

	public User getByCredentials(String username, String password) {
		Session s = factory.getCurrentSession();

		User u = null;

		Query userQuery = s.createQuery("from User u where u.username = :usrnm ", User.class);
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

	public User getByUsername(String username) {
		User u = new User();
		Session s = factory.getCurrentSession();
		Query userQuery = s.createQuery("from User u where u.username = :usrnm ", User.class);
		userQuery.setParameter("usrnm", username);
		try {
			log.info("Before getByUsername query. ");
			u = (User) userQuery.getSingleResult();
			if (u == null) {
				log.info("No User found with given username: " + username);
				return null;
			}
			log.info("After query: -------");
		} catch (NoResultException nre) {
			log.info("No User found with these credentials: UN: " + username);
			return new User();
		} catch (Exception e) {
			log.info("Exception thrown in getByCredentials when invoked with these credentials: UN: " + username);
			return new User();
		}
		return u;
	}

	public User add(User newUser) {
		Session s = factory.getCurrentSession();
		s.save(newUser);
		return newUser;
	}
	
	public User update(User updatedUser) {
		Session s = factory.getCurrentSession();
//		User user = s.get(User.class, updatedUser.getUser_id());
//		if (user == null)
//			return null;
//		else
			s.saveOrUpdate(updatedUser);
		return updatedUser;
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
