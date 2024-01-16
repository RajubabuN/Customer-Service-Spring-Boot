package com.service.customer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.service.customer.constants.CustomerCosntants;
import com.service.customer.dto.CustomerDto;
import com.service.customer.exception.CustomException;

import io.micrometer.common.util.StringUtils;

/**
 * The Class ValidationService.
 */
@Service
public class ValidationService {

	/**
	 * Validate payload.
	 *
	 * @param input the input
	 * @return true, if successful
	 * @throws CustomException the custom exception
	 */
	public boolean validatePayload(final CustomerDto input) throws CustomException {

		final Map<String, String> errorMessage = new HashMap<>();
		if (StringUtils.isBlank(input.getName())) {

			errorMessage.put("Name", "Name should not be empty");
		}
		if (StringUtils.isBlank(input.getContactNo())) {

			errorMessage.put("Name", "Contact Number should not be empty");
		} else {

			final String regexString = CustomerCosntants.CONTACT_NO_REGEX;
			final Pattern contactNoPattern = Pattern.compile(regexString);
			final Matcher matcher = contactNoPattern.matcher(input.getContactNo());
			if (!matcher.matches()) {
				errorMessage.put("ContactNo", "Contact Number should be 10 digits");
			}
		}

		if (!StringUtils.isBlank(input.getEmail())) {

			final String regexString = CustomerCosntants.EMAILD_REGEX;
			final Pattern contactNoPattern = Pattern.compile(regexString);
			final Matcher matcher = contactNoPattern.matcher(input.getEmail());
			if (!matcher.matches()) {
				errorMessage.put("EMail", "Email ID should be valid(i.e. abc@gmail.com)");
			}
		}
		if (errorMessage.isEmpty()) {
			return true;
		}
		final StringJoiner joiner = new StringJoiner(",");
		int i = 1;
		for (final Entry<String, String> map : errorMessage.entrySet()) {

			joiner.add(i + "-" + map.getValue());
			i++;
		}
		throw new CustomException(joiner.toString());
	}
}
