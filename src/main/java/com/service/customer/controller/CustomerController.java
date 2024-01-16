package com.service.customer.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.customer.dto.CustomerDto;
import com.service.customer.exception.CustomException;
import com.service.customer.interfaces.ICustomer;
import com.service.customer.service.CustomerService;

/**
 * The Class CustomerController.
 */
@RestController
@RequestMapping("/v1/api/customers/")
public class CustomerController implements ICustomer {

	/** The service. */
	private final CustomerService service;

	/**
	 * Instantiates a new customer controller.
	 *
	 * @param service the service
	 */
	public CustomerController(final CustomerService service) {

		this.service = service;
	}

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 * @throws Exception the exception
	 */
	@Override
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public CustomerDto getCustomerById(@PathVariable final Long id) throws Exception {

		return service.getCustomerById(id);
	}

	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	@Override
	@GetMapping("")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<CustomerDto> getAllCustomers() {

		return service.getAllCustomer();
	}

	/**
	 * Creates the customer.
	 *
	 * @param input the input
	 * @return the string
	 * @throws CustomException the custom exception
	 */
	@Override
	@PostMapping("")
	public String createCustomer(@RequestBody final CustomerDto input) throws CustomException {

		return service.createCustomer(input);
	}

	/**
	 * Update customer.
	 *
	 * @param input the input
	 * @return the string
	 * @throws Exception the exception
	 */
	@Override
	@PutMapping("")
	public String updateCustomer(@RequestBody final CustomerDto input) throws Exception {
		return service.updateCustomer(input);
	}

	/**
	 * Delete customer by id.
	 *
	 * @param id the id
	 * @return the string
	 * @throws Exception the exception
	 */
	@Override
	@DeleteMapping("{id}")
	public String deleteCustomerById(@PathVariable final Long id) throws Exception {
		return service.deleteCustomerById(id);
	}

	/**
	 * Call external service.
	 *
	 * @param token the token
	 * @return the string
	 * @throws JSONException the JSON exception
	 */
	@GetMapping("/fetch/salesforce/user/data")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String callExternalService(@RequestHeader("Authorization") final String token) {
		return service.callExternalService(token);
	}
}
