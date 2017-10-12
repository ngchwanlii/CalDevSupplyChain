package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.repository.RoleRepository;
import com.caldevsupplychain.account.vo.RoleName;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(config = MapperBaseConfig.class)
public abstract class RoleMapper {

	@Autowired
	private RoleRepository roleRepository;

	public RoleName toRoleName(Role role){
		return role != null ? role.getName() : null;
	}
	public Role toRole(RoleName roleName) {
		return roleName != null ? roleRepository.findByName(roleName) : null;
	}

	// for reuse purpose
	public abstract List<RoleName> toRoleNames(List<Role> roles);
	public abstract List<Role> toRoles(List<RoleName> roleNames);
}
