package com.service.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The Class RestTemplateConfig.
 */
@Configuration
public class RestTemplateConfig {

	/**
	 * Rest template.
	 *
	 * @return the rest template
	 */
	@Bean
	RestTemplate restTemplate() {

		// SSL Certs can be configured to make rest call with https
		return new RestTemplate();
	}
}
