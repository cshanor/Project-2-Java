package com.revature.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;

/**
 * This controller will help with communicating with any requests that pertains
 * to a user.
 * 
 * @Endpoint /user/login Will attempt to login a user
 * @Endpoint /user/register Will attempt to register a user
 * 
 * @author Jose Rivera
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * Method to accept a POST request to login a user. Will accept a string array
	 * that contains the user's credentials being the username and password.
	 * 
	 * @return The new user that was logged in, or null if the user was unable to be
	 *         logged in.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User loginUser(@RequestBody String[] credentials) {

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
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User registerUser(@RequestBody String[] registrationInfo) {

		return null;
	}
}
