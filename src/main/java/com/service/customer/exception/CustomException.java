package com.service.customer.exception;

/**
 * The Class CustomException.
 */
public class CustomException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param message the message
	 */
	public CustomException(final String message) {

		super(message);
	}
}
