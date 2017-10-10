package com.caldevsupplychain.account.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caldevsupplychain.account.util.UserMapper;

@Configuration
public class Config {

	@Bean
	public UserMapper userMapper() {
		return Mappers.getMapper(UserMapper.class);
	}
}
