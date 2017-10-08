package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.repository.RoleRepository;
import com.caldevsupplychain.account.vo.RoleName;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperResolver {

	@Autowired
	private RoleRepository roleRepository;

	@Named("toRole")
	public Role toRole(RoleName roleName){
		return roleName != null ? roleRepository.findByName(roleName) : null;
	}

}
