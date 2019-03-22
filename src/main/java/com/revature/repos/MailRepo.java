package com.revature.repos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Mail;

@Repository
public class MailRepo {

	private SessionFactory factory;
	
	@Autowired
	public MailRepo(SessionFactory factory) {
		this.factory = factory;
	}
	
	public List<Mail> getAll() {
		Session s = factory.getCurrentSession();
		return s.createQuery("from Mail", Mail.class).getResultList();
	}
	
	public Mail getById(int id) {
		Session s = factory.getCurrentSession();
		return s.get(Mail.class, id);
	}
	
	public Mail add(Mail newMail) {
		Session s = factory.getCurrentSession();
		s.save(newMail);
		return newMail;
	}
	
	public Mail update(Mail updatedMail) {
		Session s = factory.getCurrentSession();
		Mail mail = s.get(Mail.class, updatedMail.getMail_id());
		if(mail == null) return null;
		else mail = updatedMail; 
		return mail;
	}
	
	public boolean delete(int id) {
		Session s = factory.getCurrentSession();
		Mail mail = s.get(Mail.class, id);
		if(mail == null) return false;
		s.delete(mail);
		return true;
	}
	
}
