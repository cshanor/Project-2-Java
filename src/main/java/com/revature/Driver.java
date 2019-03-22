package com.revature;

import org.hibernate.Session;

import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class Driver {

	public static void main(String[] args) {
		// FOR TESTING
//		// Establish a session
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//
//		// Start a transaction
//		session.beginTransaction();
//
//		Profile prof = new Profile(1, "Marco", "VR", "MsiGUy", "Peanut Butter Lover", 1, "Super Cool Dude");
//		User marco = new User("marcovr", "pass", prof);
//		session.save(marco);
//		session.getTransaction().commit();
	}

}
