package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.repository.RoleRepository;
import com.caldevsupplychain.account.vo.RoleName;
import org.mapstruct.IterableMapping;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapperResolver {

	@Autowired
	private RoleRepository roleRepository;

	@Named("toRole")
	public Role toRole(RoleName roleName){
		return roleName != null ? roleRepository.findByName(roleName) : null;
	}

}
