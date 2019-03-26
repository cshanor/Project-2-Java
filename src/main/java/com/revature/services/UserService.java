package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repos.UserRepo;
import com.revature.util.AesEncryptUtil;

@Service
public class UserService {

	private UserRepo userRepo;

	@Autowired
	public UserService(UserRepo UserRepo) {
		userRepo = UserRepo;
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public List<User> getAll() {
		List<User> users = userRepo.getAll();
		for (User u : users) {
			u.setPassword(AesEncryptUtil.decrypt(u.getPassword()));
		}
		return users;
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public User getById(int id) {
		User user = userRepo.getById(id);
		user.setPassword(AesEncryptUtil.decrypt(user.getPassword()));
		return user;
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public User getByCredentials(String username, String password) {
		User user = userRepo.getByCredentials(username, password);
		user.setPassword(AesEncryptUtil.decrypt(user.getPassword()));
		return user;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public User add(User newUser) {
		newUser.setPassword(AesEncryptUtil.encrypt(newUser.getPassword()));
		return userRepo.add(newUser);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User update(User updatedUser) {
		updatedUser.setPassword(AesEncryptUtil.encrypt(updatedUser.getPassword()));
		return userRepo.update(updatedUser);
	}

	@Transactional // default isolation = READ_UNCOMMITTED; default propagation = REQUIRED
	public boolean delete(int id) {
		return userRepo.delete(id);
	}

}
