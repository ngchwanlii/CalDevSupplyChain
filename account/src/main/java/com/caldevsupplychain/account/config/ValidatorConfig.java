package com.caldevsupplychain.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caldevsupplychain.account.validator.EditUserValidator;
import com.caldevsupplychain.account.validator.LoginValidator;
import com.caldevsupplychain.account.validator.SignupValidator;

@Configuration
public class ValidatorConfig {

	@Bean
	public SignupValidator signupValidator() {
		return new SignupValidator();
	}

	@Bean
	public LoginValidator loginValidator() {
		return new LoginValidator();
	}

	@Bean
	public EditUserValidator editUserValidator() {
		return new EditUserValidator();
	}
}
