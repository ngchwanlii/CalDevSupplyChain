package com.caldevsupplychain.account.util;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.repository.RoleRepository;
import com.caldevsupplychain.account.vo.RoleName;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.ws.account.UserWS;

@Mapper(config = MapperBaseConfig.class)
public abstract class UserMapper {
	@Autowired
	private RoleRepository roleRepository;

	public abstract UserBean userWSToBean(UserWS userWS);
	public abstract UserWS userBeanToWS(UserBean userBean);
	public abstract User userBeanToUser(UserBean userBean);
	public abstract UserBean userToBean(User user);

	@Named("toRoleName")
	public RoleName toRoleName(Role role) {
		return role != null ? role.getName() : null;
	}

	@IterableMapping(qualifiedByName = "toRoleName")
	public abstract  List<RoleName> toRoleNameList(List<Role> roleList);

	@Named("toRole")
	public Role toRole(RoleName roleName){
		return roleName != null ? roleRepository.findByName(roleName) : null;
	}

	@IterableMapping(qualifiedByName = "toRole")
	public abstract List<Role> toRoleList(List<RoleName> roleNameList);
}
