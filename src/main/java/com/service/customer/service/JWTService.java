package com.service.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * The Class JWTService.
 */
@Service
public class JWTService {

	/**
	 * Generate token.
	 *
	 * @param userName the user name
	 * @return the string
	 */
	public String generateToken(final String userName) {

		final Map<String, String> claimsMap = new HashMap<>();
		claimsMap.put("upn", userName);
		return createToken(claimsMap, userName);
	}

	/**
	 * Creates the token.
	 *
	 * @param claimsMap the claims map
	 * @param userName  the user name
	 * @return the string
	 */
	public String createToken(final Map<String, String> claimsMap, final String userName) {

		return Jwts.builder().claims(claimsMap).subject(userName).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
				.signWith(Keys.hmacShaKeyFor(signKey())).compact();
	}

	/**
	 * Gets the user name.
	 *
	 * @param token the token
	 * @return the user name
	 */
	public String getUserName(final String token) {

		return parseClaim(token, Claims::getSubject);
	}

	/**
	 * Gets the token expiry.
	 *
	 * @param token the token
	 * @return the token expiry
	 */
	public Date getTokenExpiry(final String token) {

		return parseClaim(token, Claims::getExpiration);
	}

	/**
	 * Checks if is token expired.
	 *
	 * @param token the token
	 * @return true, if is token expired
	 */
	public boolean isTokenExpired(final String token) {

		return getTokenExpiry(token).compareTo(new Date()) > 0;
	}

	/**
	 * Validate token.
	 *
	 * @param token       the token
	 * @param userDetails the user details
	 * @return the boolean
	 */
	public Boolean validateToken(final String token, final UserDetails userDetails) {
		final String userName = getUserName(token);
		return (userName.equals(userDetails.getUsername()) && isTokenExpired(token));
	}

	/**
	 * Parses the claim.
	 *
	 * @param <T>    the generic type
	 * @param token  the token
	 * @param claims the claims
	 * @return the t
	 */
	public <T> T parseClaim(final String token, final Function<Claims, T> claims) {

		return claims.apply(parseAllClaimsFromToken(token));
	}

	/**
	 * Parses the all claims from token.
	 *
	 * @param token the token
	 * @return the claims
	 */
	public Claims parseAllClaimsFromToken(final String token) {
		return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(signKey())).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * Sign key.
	 *
	 * @return the byte[]
	 */
	public byte[] signKey() {
		final String keyString = "ebf3472805d1641683a859c0437e6610df300aa433ab8e7805b308562bc51421a";
		return Decoders.BASE64.decode(keyString);
	}
}
