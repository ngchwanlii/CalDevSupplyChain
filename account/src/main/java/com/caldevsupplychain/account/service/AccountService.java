package com.caldevsupplychain.account.service;


import java.util.List;
import java.util.Optional;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.vo.RoleName;
import com.caldevsupplychain.account.vo.UserBean;

public interface AccountService {
	boolean userExist(String emailAddress);

	UserBean createUser(UserBean userBean);

	UserBean updateUser(UserBean userBean);

	void activateUser(long id);

	Optional<UserBean> findByUuid(String uuid);

	Optional<UserBean> findByEmailAddress(String emailAddress);

	Optional<UserBean> findByToken(String token);
}
