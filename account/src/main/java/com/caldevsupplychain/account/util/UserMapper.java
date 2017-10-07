package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.ws.account.UserWS;
import com.google.common.collect.Lists;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

	@Mapping(target="roles", source="role")
	UserBean userWSToUserBean(UserWS userWS);

	// custom convert method
	default List<Role> mapStringRoleToListRole(String role) {
		return Lists.newArrayList(new Role(role));
	}

	@InheritInverseConfiguration
	UserWS userBeanToUserWS(UserBean userBean);

	default String mapListRoleToStringRole(List<Role> roles) {
		return roles.get(0).getName().toString();
	}

	UserBean userToUserBean(User user);
}
