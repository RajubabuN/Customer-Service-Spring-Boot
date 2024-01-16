package com.service.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.customer.entity.UserInfo;
import com.service.customer.service.UserRegistrationService;

/**
 * The Class UserRegistrationController.
 */
@RestController
@RequestMapping("/admin")
public class UserRegistrationController {

	/** The service. */
	@Autowired
	UserRegistrationService service;

	/**
	 * Creates the user.
	 *
	 * @param input the input
	 * @return the string
	 */
	@PostMapping("create/user")
	public String createUser(@RequestBody final UserInfo input) {

		return service.createUser(input);
	}
}
