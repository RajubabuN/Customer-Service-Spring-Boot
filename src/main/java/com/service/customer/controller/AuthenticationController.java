package com.service.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.customer.dto.TokenRequest;
import com.service.customer.service.JWTService;

/**
 * The Class AuthenticationController.
 */
@RestController
@RequestMapping("/auth/")
public class AuthenticationController {

	/** The jwt service. */
	@Autowired
	JWTService jwtService;

	/** The authentication manager. */
	@Autowired
	AuthenticationManager authenticationManager;

	/**
	 * Generate token.
	 *
	 * @param req the req
	 * @return the string
	 * @throws Exception the exception
	 */
	@PostMapping("generate/token")
	public String generateToken(@RequestBody final TokenRequest req) throws Exception {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getUserName(), req.getPassword()));

		return jwtService.generateToken(req.getUserName());

	}
}
