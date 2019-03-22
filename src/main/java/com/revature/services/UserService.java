package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.User;
import com.revature.repos.UserRepo;

@Service
public class UserService {
	
	private UserRepo userRepo;
	
	@Autowired
	public UserService(UserRepo UserRepo) {
		userRepo = UserRepo;
	}
	
	@Transactional(readOnly=true, isolation=Isolation.SERIALIZABLE)
	public List<User> getAll() {
		return userRepo.getAll();
	}
	
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	public User getById(int id) {
		return userRepo.getById(id);
	}
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED, propagation=Propagation.REQUIRED)
	public User add(User newUser) {
		return userRepo.add(newUser);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User update(User updatedUser) {
		return userRepo.update(updatedUser);
	}
	
	@Transactional // default isolation = READ_UNCOMMITTED; default propagation = REQUIRED
	public boolean delete(int id) {
		return userRepo.delete(id);
	}


}
