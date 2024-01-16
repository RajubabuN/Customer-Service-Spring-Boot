
package com.service.customer.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.service.customer.entity.UserInfo;
import com.service.customer.repository.UserInfoRepository;

/**
 * The Class UserDetailsServiceImpl.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	/** The user repository. */
	@Autowired
	UserInfoRepository userRepository;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final Optional<UserInfo> userDetails = userRepository.findByName(username);
		return userDetails.map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid User:" + username));

	}

}
