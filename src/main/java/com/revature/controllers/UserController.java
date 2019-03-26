package com.revature.controllers;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.JwtConfig;
import com.revature.util.JwtGenerator;

/**
 * This controller will help with communicating with any requests that pertains
 * to a user.
 * 
 * @Endpoint /user/auth Will attempt to login a user
 * @Endpoint /user/add Will attempt to register a user
 * 
 * @author Jose Rivera
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	// Access to our user service
	private UserService service;

	/**
	 * Constructor for the UserController. Autowired to our UserService object
	 * 
	 * @param userService
	 */
	@Autowired
	public UserController(UserService userService) {
		this.service = userService;
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
	public User login (@RequestBody User user, HttpServletResponse resp) {
		//User user = service.getByCredentials(username, password);
		service.getByCredentials(user.getUsername(), user.getPassword());
		resp.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + JwtGenerator.createJwt(user));
		resp.addHeader("Info", Integer.toString(user.getUser_id()));
		resp.addHeader("UserName", user.getUsername());
		return user;
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
	
	// TEST
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
	public String test() {

		return "This is a test";
	}
	
	
	

	/**
	 * Method to accept a GET request to retrieve the Friends List for the given user. 
	 * Will accept a user object, which could be changed to just send a user id in future iterations. 
	 * 
	 * @return The current friends list, or null if the user was unable to be
	 *         added to the database.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/friends", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getUserFriends(@RequestBody User user, HttpServletResponse resp) {
		List<User> friends = null; 
		try { //Depending on the mapping of the list of objects, this try block may not be entirely necessary.  
		 friends = service.getFriendsByUsername(user.getUser_id());
		 PrintWriter out = resp.getWriter();		 
		 out.print(friends);
		 resp.setStatus(200);
		 
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(500);
		} 
		
		
		
		return friends;
	}
	
}
