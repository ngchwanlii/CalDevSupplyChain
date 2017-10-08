package com.caldevsupplychain.account.service;


import com.caldevsupplychain.account.vo.UserBean;

import java.util.Optional;

public interface AccountService {
	boolean userExist(String emailAddress);

	UserBean createUser(UserBean userBean);

	UserBean updateUser(UserBean userBean);

	void activateUser(long id);

	Optional<UserBean> findByUuid(String uuid);

	Optional<UserBean> findByEmailAddress(String emailAddress);

	Optional<UserBean> findByToken(String token);
}
