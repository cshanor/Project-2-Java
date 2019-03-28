package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;
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
	private Logger log = Logger.getLogger(UserService.class);

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
	// Brandon injection has occurred here. The following code has been
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

		// if the user is returned null, break out of the method via return.
		if (user == null)
			return null;

		// Do not try to access the password if the user is null.
		// Doing so will cause a null pointer exception.
		// if(user.getPassword() == null) return new User(0,"EmptyUser","UsernotFound");

		// if the user is not null, decrypt their password.
		user.setPassword(AesEncryptUtil.decrypt(user.getPassword()));

		// if(user==null) {log.info("getByUsername(" + username + " ) came back null. "
		// ); return new User();}

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

		// Check for duplicate username before trying to add
		if (isDuplicateUsername(newUser.getUsername()))
			return null;

		// Encrypt their password
		newUser.setPassword(AesEncryptUtil.encrypt(newUser.getPassword()));
		
		// Create a basic profile for the user
		Profile newProfile = new Profile();
		profileService.add(newProfile);
		newUser.setProfile_id(newProfile);

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

	/**
	 * Method to check for duplicate usernames. This method will only check for a
	 * user with the same username that was passed in. This could be better
	 * accomplished through the use of the UNIQUE constraint, but currently that
	 * causes other issues.
	 * 
	 * @param username The username to check
	 * @return True if the username exists, false if not
	 */
	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public boolean isDuplicateUsername(String username) {

		if (userRepo.getByUsername(username) == null)
			return false;

		return true;
	}
}
