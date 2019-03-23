package com.revature.controllers;

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
		service = userService;
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
	public User loginUser(@RequestBody User user) {

		return null;
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

		// Send the user object through the service
		return service.add(user);
	}
	
	// TEST
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public String test() {

		return "This is a test";
	}
}
