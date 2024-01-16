package com.service.customer.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * The Class ExceptionHandler.
 */
@ControllerAdvice
public class ExceptionHandler {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	/**
	 * Handle exception.
	 *
	 * @param ex      the ex
	 * @param request the request
	 * @return the response entity
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleException(final Exception ex, final WebRequest request) {

		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Data access exception.
	 *
	 * @param ex      the ex
	 * @param request the request
	 * @return the response entity
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
	public final ResponseEntity<Object> dataAccessException(final DataAccessException ex, final WebRequest request) {

		log.error(ex.getMessage());
		final Map<Object, Object> responseMap = new HashMap<>();
		responseMap.put("Error Details", "Provide valid customer details");
		responseMap.put("Stack Trace", ex.getMessage());
		return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Custom exception.
	 *
	 * @param ex      the ex
	 * @param request the request
	 * @return the response entity
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
	public final ResponseEntity<Object> customException(final CustomException ex, final WebRequest request) {

		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
