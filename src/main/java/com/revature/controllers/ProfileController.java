package com.revature.controllers;

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

import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.services.ProfileService;
import com.revature.util.JwtConfig;
import com.revature.util.JwtGenerator;

/**
 * This controller will help with communicating with any requests that pertains
 * to a user.
 * 
 * @Endpoint /profile/get Will attempt to get a profile
 * @Endpoint /profile/update Will attempt to register a user
 * 
 * @author Marco Van Rhyn
 *
 */
@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

	// Access to our user service
	private ProfileService service;

	/**
	 * Constructor for the UserController. Autowired to our UserService object
	 * 
	 * @param userService
	 */
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.service = profileService;
	}

	/**
	 * Method to accept a GET request to retrieve a profile. Will accept a profile o.
	 * 
	 * @return The profile that was retrieved by id, or null if it could not be found.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Profile GetProfile(@RequestBody int profileId, HttpServletResponse resp) {
		Profile getProfile = service.getById(profileId);
		resp.addHeader("profile", getProfile.toString());
		return getProfile;
	}

	/**
	 * Method to accept a POST request to update a profile. Will accept a string
	 * array that contains the profile information to update a profile and persist it to the
	 * database.
	 * 
	 * @return The profile that was updated, or null if the profile was unable to be
	 *         updated in the database.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Profile registerUser(@RequestBody Profile profile) {

		return service.update(profile);
	}

	// TEST
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
	public String test() {

		return "This is a test";
	}
}
