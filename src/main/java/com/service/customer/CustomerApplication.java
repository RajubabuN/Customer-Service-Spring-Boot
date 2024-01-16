package com.service.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class CustomerApplication.
 */
@SpringBootApplication(scanBasePackages = "com.service.customer")
public class CustomerApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
