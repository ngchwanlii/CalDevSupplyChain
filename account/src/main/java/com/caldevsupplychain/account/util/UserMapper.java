package com.caldevsupplychain.account.util;

import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.ws.account.UserWS;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = MapperBaseConfig.class, uses = {RoleMapper.class})
public interface UserMapper {

	@Named("userWSToBean")
	UserBean userWSToBean(UserWS userWS);
	@Named("userBeanToWS")
	UserWS userBeanToWS(UserBean userBean);
	@Named("userBeanToUser")
	User userBeanToUser(UserBean userBean);
	@Named("userToBean")
	UserBean userToBean(User user);

	@IterableMapping(qualifiedByName = "userWSToBean")
	List<UserBean> userWSsToUserBeans(List<UserWS> userWS);
	@IterableMapping(qualifiedByName = "userBeanToWS")
	List<UserWS> userBeansToUserWSs(List<UserBean> userBean);
	@IterableMapping(qualifiedByName = "userBeanToUser")
	List<User> userBeansToUsers(List<UserBean> userBean);
	@IterableMapping(qualifiedByName = "userToBean")
	List<UserBean> usersToUserBeans(List<User> user);


}
