
package com.service.customer.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.service.customer.config.UserDetailsServiceImpl;
import com.service.customer.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The Class JwtAuthFilter.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	/** The jwt service. */
	@Autowired
	JWTService jwtService;

	/** The user details service impl. */
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * Do filter internal.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param filterChain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		final String bearerToken = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		if ((null != bearerToken) && bearerToken.contains("Bearer ")) {

			token = bearerToken.substring(7);
			userName = jwtService.getUserName(token);
		}
		if ((null != userName) && (null == SecurityContextHolder.getContext().getAuthentication())) {

			final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
			if (jwtService.validateToken(token, userDetails)) {
				final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
