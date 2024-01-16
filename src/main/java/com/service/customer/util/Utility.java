package com.service.customer.util;

import com.service.customer.dto.CustomerDto;
import com.service.customer.entity.Customer;

/**
 * The Class Utility.
 */
public class Utility {

	/**
	 * Covert entity to dto.
	 *
	 * @param data the data
	 * @return the customer dto
	 */
	public static CustomerDto covertEntityToDto(final Customer data) {
		final CustomerDto dto = new CustomerDto();
		dto.setId(data.getId());
		dto.setName(data.getName());
		dto.setEmail(data.getEmail());
		dto.setContactNo(data.getContactNo());
		return dto;
	}

}
