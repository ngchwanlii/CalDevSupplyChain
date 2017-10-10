package com.caldevsupplychain.account.config;

import com.caldevsupplychain.account.util.RoleMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caldevsupplychain.account.util.UserMapper;

@Configuration
public class MapperConfig {

	@Bean
	public UserMapper userMapper() {
		return Mappers.getMapper(UserMapper.class);
	}

	@Bean
	public RoleMapper roleMapper(){
		return Mappers.getMapper(RoleMapper.class);
	}
}
