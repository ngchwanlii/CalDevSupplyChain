package com.caldevsupplychain.account.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.caldevsupplychain.account.util.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.repository.RoleRepository;
import com.caldevsupplychain.account.repository.UserRepository;
import com.caldevsupplychain.account.util.UserMapper;
import com.caldevsupplychain.account.vo.UserBean;
import com.caldevsupplychain.common.type.ErrorCode;
import com.google.common.base.Preconditions;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordService passwordService;
	private UserMapper userMapper;
	private RoleMapper roleMapper;

	@Override
	public boolean userExist(String emailAddress) {
		return userRepository.findByEmailAddress(emailAddress) != null;
	}

	@Override
	@Transactional
	public UserBean createUser(UserBean userBean) {

		Preconditions.checkState(!userBean.getRoles().isEmpty(), "Must assign at least one role when creating a user.");
		List<Role> roleList = roleRepository.findByName(userBean.getRoles());

		if (roleList == null) {
			return null;
		}

		userBean.setToken(UUID.randomUUID().toString());
		userBean.setPassword(passwordService.encryptPassword(userBean.getPassword()));

		User user = userMapper.userBeanToUser(userBean);

		userRepository.save(user);

		return userMapper.userToBean(user);
	}

	@Override
	@Transactional
	public UserBean updateUser(UserBean userBean) {

		User user = userRepository.findByEmailAddress(userBean.getEmailAddress());

		Preconditions.checkState(user != null, ErrorCode.USER_NOT_FOUND.toString());

		user.setUsername(userBean.getUsername());
		user.setPassword(passwordService.encryptPassword(userBean.getPassword()));
		Optional.ofNullable(userBean.getRoles()).ifPresent(roleNames -> user.setRoles(roleMapper.toRoleList(roleNames)));

		return userMapper.userToBean(user);
	}

	@Override
	@Transactional
	public void activateUser(long id) {
		User user = userRepository.findOne(id);
		Preconditions.checkState(user != null, "[activateUser Error]: User with id %s not found.", id);
		user.setToken(null);
	}

	@Override
	public Optional<UserBean> findByUuid(String uuid) {
		User user = userRepository.findByUuid(uuid);
		if (user != null) {
			return Optional.of(userMapper.userToBean(user));
		}
		return Optional.empty();
	}

	@Override
	public Optional<UserBean> findByEmailAddress(String emailAddress) {
		User user = userRepository.findByEmailAddress(emailAddress);
		if (user != null) {
			return Optional.of(userMapper.userToBean(user));
		}
		return Optional.empty();
	}

	@Override
	public Optional<UserBean> findByToken(String token) {
		User user = userRepository.findByToken(token);
		if (user != null) {
			return Optional.of(userMapper.userToBean(user));
		}
		return Optional.empty();
	}
}
