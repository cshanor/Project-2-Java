package com.revature.services;

import java.sql.SQLIntegrityConstraintViolationException;
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
	private ProfileService profileService;

	@Autowired
	public UserService(UserRepo UserRepo, ProfileService profileService) {
		this.userRepo = UserRepo;
		this.profileService = profileService;

	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public List<User> getAll() {
		List<User> users = userRepo.getAll();
		for (User u : users) {
			u.setPassword(AesEncryptUtil.decrypt(u.getPassword()));
		}
		return users;
	}

	// ---------------------------------------------------------------
	// Brandon injection has occured here. The following code has been
	// injected into your class via Brandon.

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<User> getFriendsByUser_Id(int user_id) {
		List<User> friends = userRepo.getFriendsByUser_Id(user_id);
		// Iterate thru the friends at this point and set all passwords to hidden,
		// masking them with the phrase *NoWayJose*
		/*
		 * for(User u : friends) { u.setPassword("*NoWayJose*");
		 * 
		 * }
		 */

		return friends;
	}

	/**
	 * Method needed to get a friend by the passed in username. If a user wishes to
	 * add a friend to their friend's list, he must input that user's username.
	 * 
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public User getByUsername(String username) {
		User user = userRepo.getByUsername(username);
		user.setPassword(AesEncryptUtil.decrypt(user.getPassword()));
		return user;
	}
	// --------------------------------------------------------------

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

		// Check if the user is already in the data base
		User u = this.getByUsername(newUser.getUsername());

		// If the user didn't return as a null value, it already exists
		try {
			if (u != null) {
				throw new SQLIntegrityConstraintViolationException();
			} else {
				newUser.setPassword(AesEncryptUtil.encrypt(newUser.getPassword()));
				Profile newProf = new Profile();
				profileService.add(newProf);
				newUser.setProfile_id(newProf);
			}
		} catch (SQLIntegrityConstraintViolationException sicve) {
			System.out.println(sicve.getMessage());
			return null;
		}

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
