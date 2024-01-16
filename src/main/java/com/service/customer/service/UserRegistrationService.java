package com.service.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.customer.entity.UserInfo;
import com.service.customer.repository.UserInfoRepository;

/**
 * The Class UserRegistrationService.
 */
@Service
public class UserRegistrationService {

	/** The repo. */
	@Autowired
	private UserInfoRepository repo;

	/** The encoder. */
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * Creates the user.
	 *
	 * @param input the input
	 * @return the string
	 */
	public String createUser(final UserInfo input) {

		input.setPassword(encoder.encode(input.getPassword()));
		repo.save(input);
		return "User registered successfully";
	}
}
