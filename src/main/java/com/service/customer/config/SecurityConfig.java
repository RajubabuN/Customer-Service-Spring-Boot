
package com.service.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.service.customer.filter.JwtAuthFilter;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	/** The permit all url. */
	@Value("${url.swagger.permitall:/**}")
	private String[] permitAllUrl;

	/** The filter. */
	@Autowired
	JwtAuthFilter filter;

	/**
	 * Config chain.
	 *
	 * @param httpSecurity the http security
	 * @return the security filter chain
	 * @throws Exception the exception
	 */
	@Bean
	SecurityFilterChain configChain(final HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
				.headers(h -> h.frameOptions(k -> k.disable()))
				.authorizeHttpRequests(request -> request.requestMatchers(permitAllUrl).permitAll())
				.authorizeHttpRequests(
						request -> request.requestMatchers(HttpMethod.POST, "/v1/api/**").hasRole("ADMIN"))
				.authorizeHttpRequests(
						request -> request.requestMatchers(HttpMethod.PUT, "/v1/api/**").hasRole("ADMIN"))

				.authorizeHttpRequests(request -> request.anyRequest().authenticated())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
	}

	/**
	 * Encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Authentication manager.
	 *
	 * @param authConfig the auth config
	 * @return the authentication manager
	 * @throws Exception the exception
	 */
	@Bean
	AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * User details.
	 *
	 * @return the user details service
	 */
	@Bean
	UserDetailsService userDetails() {
		return new UserDetailsServiceImpl();
	}

	/**
	 * Authentication provider.
	 *
	 * @return the authentication provider
	 */
	@Bean
	AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetails());
		authenticationProvider.setPasswordEncoder(encoder());
		return authenticationProvider;
	}

}
