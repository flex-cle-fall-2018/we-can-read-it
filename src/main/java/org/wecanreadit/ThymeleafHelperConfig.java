package org.wecanreadit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class ThymeleafHelperConfig {

	// Enables Thymeleaf layouts (must be manually included since Spring Boot 2)
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
}