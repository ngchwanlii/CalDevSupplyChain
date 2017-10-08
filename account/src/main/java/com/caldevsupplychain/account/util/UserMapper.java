package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.vo.RoleName;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.ws.account.UserWS;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MapperBaseConfig.class, uses = RoleMapperResolver.class)
public interface UserMapper {

	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

	UserBean userWSToBean(UserWS userWS);
	UserWS userBeanToWS(UserBean userBean);
	User userBeanToUser(UserBean userBean);
	UserBean userToBean(User user);

	/****************************************************************************************************************
	 * Converter                                                                                                    *
	 * Note: RoleName -> Role involve database query so put it inside RoleMapperResolver                            *
	 *                                                                                                              *
	 ****************************************************************************************************************/
	@Named("toRoleName")
	default RoleName toRoleName(Role role) {
		return role != null ? role.getName() : null;
	}

	@IterableMapping(qualifiedByName = "toRoleName")
	List<RoleName> toRoleNameList(List<Role> roleList);

	@IterableMapping(qualifiedByName = "toRole")
	List<Role> toRoleList(List<RoleName> roleNameList);

}
