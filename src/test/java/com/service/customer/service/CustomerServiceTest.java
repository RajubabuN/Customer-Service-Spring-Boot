package com.service.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.service.customer.entity.Customer;
import com.service.customer.repository.CustomerRepository;

class CustomerServiceTest {

	@InjectMocks
	CustomerService service;

	@Mock
	CustomerRepository repo;

	@Mock
	ValidationService validationService;

	@Mock
	RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getCustomerByIdTest() throws Exception {

		final Customer customer = new Customer();
		customer.setContactNo("1234567890");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
		assertEquals("1234567890", service.getCustomerById(1L).getContactNo());
	}

	@Test
	void getCustomerByIdTestWithNoID() throws Exception {

		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(Exception.class, () -> service.getCustomerById(1L).getContactNo());
	}
}
