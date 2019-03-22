package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Mail;
import com.revature.models.User;
import com.revature.repos.MailRepo;

public class MailService {
	
private MailRepo mailRepo;
	
	@Autowired
	public MailService(MailRepo MailRepo) {
		mailRepo = MailRepo;
	}
	
	@Transactional(readOnly=true, isolation=Isolation.SERIALIZABLE)
	public List<Mail> getAll() {
		return mailRepo.getAll();
	}
	
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public Mail getById(int id) {
		return mailRepo.getById(id);
	}
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED, propagation=Propagation.REQUIRED)
	public Mail add(Mail newMail) {
		return mailRepo.add(newMail);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Mail update(Mail updatedMail) {
		return mailRepo.update(updatedMail);
	}
	
	@Transactional // default isolation = READ_UNCOMMITTED; default propagation = REQUIRED
	public boolean delete(int id) {
		return mailRepo.delete(id);
	}

}
