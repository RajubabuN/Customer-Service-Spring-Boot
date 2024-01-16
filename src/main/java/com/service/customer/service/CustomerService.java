package com.service.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.service.customer.constants.CustomerCosntants;
import com.service.customer.dto.CustomerDto;
import com.service.customer.entity.Customer;
import com.service.customer.exception.CustomException;
import com.service.customer.repository.CustomerRepository;
import com.service.customer.util.Utility;

/**
 * The Class CustomerService.
 */
@Service
public class CustomerService {

	@Value("${url.external.api:null}")
	private String externalAPIUrl;

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	/** The repo. */
	private final CustomerRepository repo;

	/** The validation service. */
	private final ValidationService validationService;

	/** The rest template. */
	private final RestTemplate restTemplate;

	/**
	 * Instantiates a new customer service.
	 *
	 * @param repo              the repo
	 * @param validationService the validation service
	 * @param restTemplate      the rest template
	 */
	CustomerService(final CustomerRepository repo, final ValidationService validationService,
			final RestTemplate restTemplate) {

		this.repo = repo;
		this.validationService = validationService;
		this.restTemplate = restTemplate;
	}

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 * @throws Exception the exception
	 */
	public CustomerDto getCustomerById(final Long id) throws Exception {

		return repo.findById(id).map(Utility::covertEntityToDto)
				.orElseThrow(() -> new Exception(CustomerCosntants.CUSTOMER_NOT_FOUND));
	}

	/**
	 * Gets the all customer.
	 *
	 * @return the all customer
	 */
	public List<CustomerDto> getAllCustomer() {

		return repo.findAll().stream().map(Utility::covertEntityToDto).collect(Collectors.toList());
	}

	/**
	 * Creates the customer.
	 *
	 * @param input the input
	 * @return the string
	 * @throws CustomException the custom exception
	 */
	public String createCustomer(final CustomerDto input) throws CustomException {

		return createOrUpdateCustomer(input, new Customer(), true);
	}

	/**
	 * Creates the or update customer.
	 *
	 * @param input            the input
	 * @param entity           the entity
	 * @param isCreateCustomer the is create customer
	 * @return the string
	 * @throws CustomException the custom exception
	 */
	public String createOrUpdateCustomer(final CustomerDto input, final Customer entity, final boolean isCreateCustomer)
			throws CustomException {

		if (validationService.validatePayload(input)) {

			entity.setName(input.getName());
			entity.setContactNo(input.getContactNo());
			entity.setEmail(input.getEmail());

			final Customer dbRecordCustomer = repo.save(entity);
			log.debug("Customer created successfully Name:" + dbRecordCustomer.getName() + " Id:"
					+ dbRecordCustomer.getId());
			if (isCreateCustomer) {
				return CustomerCosntants.CUSTOMER_CREATED_SUCCESSFULLY;
			}
			return CustomerCosntants.CUSTOMER_UPDATED_SUCCEFULLY;
		}

		throw new CustomException("Provide valid customer Details");
	}

	/**
	 * Update customer.
	 *
	 * @param input the input
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateCustomer(final CustomerDto input) throws Exception {

		if (input.getId() != 0) {
			final Optional<Customer> customer = repo.findById(input.getId());

			if (customer.isEmpty()) {
				throw new Exception(CustomerCosntants.CUSTOMER_NOT_FOUND);
			} else {
				return createOrUpdateCustomer(input, customer.get(), false);
			}
		}
		throw new Exception(CustomerCosntants.CUSTOMER_ID_NOT_VALID);

	}

	/**
	 * Delete customer by id.
	 *
	 * @param id the id
	 * @return the string
	 * @throws Exception the exception
	 */
	public String deleteCustomerById(final Long id) throws Exception {

		if (id != 0) {
			repo.deleteById(id);
			return CustomerCosntants.CUSTOMER_DELETED_SUCCEFULLY;
		}
		throw new Exception(CustomerCosntants.CUSTOMER_ID_NOT_VALID);

	}

	/**
	 * Call external service.
	 *
	 * @param token the token
	 * @return the string
	 * @throws JSONException the JSON exception
	 */
	public String callExternalService(final String token) {

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);

		final HttpEntity<String> entity = new HttpEntity<String>("", headers); // JSONObject can be passed
		try {
			return restTemplate.exchange(externalAPIUrl, HttpMethod.POST, entity, String.class).getBody();
		} catch (final RestClientException e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
