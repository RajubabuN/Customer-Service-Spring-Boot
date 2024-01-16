package com.service.customer.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.service.customer.dto.CustomerDto;
import com.service.customer.exception.CustomException;

/**
 * The Interface ICustomer.
 */
public interface ICustomer {

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 * @throws Exception the exception
	 */
	public CustomerDto getCustomerById(final Long id) throws Exception;

	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	public List<CustomerDto> getAllCustomers();

	/**
	 * Creates the customer.
	 *
	 * @param input the input
	 * @return the string
	 * @throws CustomException the custom exception
	 */
	public String createCustomer(final CustomerDto input) throws CustomException;

	/**
	 * Update customer.
	 *
	 * @param input the input
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateCustomer(final CustomerDto input) throws Exception;

	/**
	 * Delete customer by id.
	 *
	 * @param id the id
	 * @return the string
	 * @throws Exception the exception
	 */
	public String deleteCustomerById(@PathVariable final Long id) throws Exception;

}
