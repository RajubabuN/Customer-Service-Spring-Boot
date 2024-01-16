
package com.service.customer.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.service.customer.dto.CustomerDto;
import com.service.customer.service.CustomerService;
import com.service.customer.service.JWTService;

@ContextConfiguration

@WebAppConfiguration

@SpringBootTest

@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

	@Mock
	CustomerService service;

	private MockMvc mockMvc;

	@Autowired
	JWTService jwtService;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	void setup() {

		MockitoAnnotations.openMocks(this);
		// mockMvc =MockMvcBuilders.standaloneSetup(new // //
		// CustomerController(service)).build();

		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	void getCustomerByIdTestWithUserAccess() throws Exception {
		final JWTService jwtService1 = new JWTService();
		final String tokenString = jwtService1.generateToken("user");
		Mockito.when(service.getCustomerById(Mockito.anyLong())).thenReturn(new CustomerDto());
		mockMvc.perform(get("/v1/api/customers/3").header("Authorization", tokenString))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	void getCustomerByIdTestWithAdminAccess() throws Exception {
		Mockito.when(service.getCustomerById(Mockito.anyLong())).thenReturn(new CustomerDto());
		mockMvc.perform(get("/v1/api/customers/3").with(user("admin").password("admin").roles("ADMIN")))
				.andExpect(status().isOk());

	}
}
