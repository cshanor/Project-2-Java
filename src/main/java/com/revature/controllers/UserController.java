package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.UpdateFriendsDTO;
import com.revature.models.Tag;
import com.revature.models.User;
import com.revature.services.TagService;
import com.revature.services.UserService;
import com.revature.util.JwtConfig;
import com.revature.util.JwtGenerator;

/**
 * This controller will help with communicating with any requests that pertains
 * to a user.
 * 
 * @Endpoint /user/auth Will attempt to login a user
 * @Endpoint /user/add Will attempt to register a user
 * @Endpoint /user/friends GET will get the list of friends, POST will add a
 *           friend to the user's friends list
 * @Endpoint /user/tags will GET the tags from the database
 * 
 * @author Jose Rivera
 * 
 * @author Brandon Morris
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class);
	private UserService service;
	private TagService tagService;

	@Autowired
	public UserController(UserService userService, TagService tagService) {
		this.service = userService;
		this.tagService = tagService;
	}

	/**
	 * Method to accept a POST request to login a user. Will accept a string array
	 * that contains the user's credentials being the username and password.
	 * 
	 * @return The new user that was logged in, or null if the user was unable to be
	 *         logged in.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User login(@RequestBody User user, HttpServletResponse resp) {
		User authUser = service.getByCredentials(user.getUsername(), user.getPassword());

		// Check if the user was valid, if not return null
		if (authUser == null) {
			return null;
		}

		// Set the headers
		resp.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + JwtGenerator.createJwt(authUser));
		resp.addHeader("User_ID", Integer.toString(authUser.getUser_id()));
		resp.addHeader("Username", authUser.getUsername());
		resp.addHeader("Profile_ID", Integer.toString(authUser.getProfile_id().getProfile_id()));
		return authUser;
	}

	/**
	 * Method to accept a POST request to register a user. Will accept a string
	 * array that contains the user information to create a user and add them to the
	 * database.
	 * 
	 * @return The new user that was added, or null if the user was unable to be
	 *         added to the database.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User registerUser(@RequestBody User user) {

		return service.add(user);
	}

	/**
	 * Method to accept a GET request to retrieve the Friends List for the given
	 * user. Will accept a user_id, which will be used to get the friends by user_is
	 * from the Junction Table User_Friends
	 * 
	 * @return The current friends list, or null if the user was unable to be added
	 *         to the database.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/friends", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getUserFriends(@RequestBody User user, HttpServletResponse resp) {

		List<User> friends = null;
		try {
			friends = service.getFriendsByUser_Id(user.getUser_id());

			resp.setStatus(200);

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(500);
		}

		return friends;
	}

	/**
	 * 
	 * @author Brandon Morris
	 * Method to accept a POST request to add a friend to the current user's Friends
	 * List. Will accept a user_id.
	 * 
	 * @deprecated As of 2019.28.03. 
	 * This feature abandoned to focus on other concerns.
	 *  
	 * @param Request Body. Takes in a data transfer object 
	 * containing a User object and a String 
	 * for the friend to add.  
	 * 
	 * @return The current friends list, or null if the user was unable to be added
	 *         to the database.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/friends", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> updateUserFriends(@RequestBody UpdateFriendsDTO updateFriendsDTO, HttpServletResponse resp) {
		List<User> friends = null;
		log.info("======= Within updateUserFriends ====== \n " + "\t RequestBody: UpdateFriendsDTO:  "
				+ updateFriendsDTO);
		User user = service.getById(updateFriendsDTO.getUser().getUser_id());

		String friend_username = updateFriendsDTO.getFriend_username();
		// takes in a user to add, get that user by username from db.

		// get that user's id, add that to the junction table.
		// User u =

		// newFriend = service.getByUsername(newFriend.getUsername());
		try {
			// fill out the rest of the details about the
			user = service.getById(user.getUser_id());

			friends = service.getFriendsByUser_Id(user.getUser_id());
			// get current friends,
			friends.add(service.getByUsername(friend_username));
			// add friend to list.

			// update the user's friends list entry into user_friends table user.user_id and
			// friend_id <- (getByUsername.getUser_id())
			service.update(user);

			// ---------------------------------------------------
			User friend = service.getByUsername(friend_username);
			friend.addFriend(user);
			service.update(friend);

			resp.setStatus(200);

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(500);
			return null;
		}

		return friends;
	}

	/**
	 * Method to accept a GET request to receive all the tags that have been stored
	 * in the database
	 * 
	 * @return A list of all the tags, or an empty list if no tags are found
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Tag> getTags() {

		return tagService.getAll();
	}

	/**
	 * Method to accept a POST request to receive an id to get the tag and add it to
	 * the user's tag
	 * 
	 * @return A valid tag, or null if no tag is found
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/tags/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Tag subscribeToGame(@RequestBody String[] ids) {
		
		int userId = Integer.parseInt(ids[0]);
		int tagId = Integer.parseInt(ids[1]);
		
		// Get the tag by id that the user requested
		Tag t = tagService.getById(tagId);
		
		// Check if the tag is valid
		if(t == null) {
			return null;
		}
		
		// Get the user that made the request by user id
		User u = service.getById(userId);
		
		// Check if that user is valid
		if(u == null) {
			return null;
		}
		
		// Add the tag to the list of user tags
		u.getTags().add(t);
		
		// Update the user
		service.update(u);
		
		// Return the valid tag
		return t;
	}
	
	/**
	 * Method to accept a POST request to return all the user's tags
	 * 
	 * @return A valid tag list, or null if no tag list is found
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/tags/getforuser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Tag> subscribeToGame(@RequestBody int id) {
		
		User u = service.getById(id);
		
		if (u == null) {
			return null;
		}
		
		return u.getTags();
		
	}
}
