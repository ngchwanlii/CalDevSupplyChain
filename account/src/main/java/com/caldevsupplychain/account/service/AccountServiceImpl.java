package com.caldevsupplychain.account.service;

import com.caldevsupplychain.account.model.Company;
import com.caldevsupplychain.account.model.Permission;
import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.repository.PermissionRepository;
import com.caldevsupplychain.account.repository.RoleRepository;
import com.caldevsupplychain.account.repository.UserRepository;
import com.caldevsupplychain.account.vo.PermissionName;
import com.caldevsupplychain.account.vo.RoleName;
import com.caldevsupplychain.common.bean.account.UserBean;
import com.caldevsupplychain.common.type.ErrorCode;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PermissionRepository permissionRepository;
	private PasswordService passwordService;

	@Override
	public boolean userExist(String emailAddress) {
	   return userRepository.findByEmailAddress(emailAddress) != null;
	}


	@Override
	@Transactional
	public User createUser(UserBean userBean) {

		Role role = roleRepository.findByName(RoleName.valueOf(userBean.getRole()));

		if (role == null) {
			return null;
		}

		// set user
		User user = new User();
		user.setUsername(userBean.getUsername());
		user.setEmailAddress(userBean.getEmailAddress());
		user.setPassword(passwordService.encryptPassword(userBean.getPassword()));
		user.setToken(UUID.randomUUID().toString());
		user.setRoles(Lists.newArrayList(role));

		// save to user repository
		userRepository.save(user);

		return user;
	}


	@Override
	@Transactional
	@RequiresPermissions("account:update")
	public User updateUser(UserBean userBean) {

		User user = userRepository.findByEmailAddress(userBean.getEmailAddress());
		Role role = roleRepository.findByName(RoleName.valueOf(userBean.getRole()));
//		Permission permission = permissionRepository.findByName(PermissionName.ACCOUNT_READ);

		Preconditions.checkState(user != null, ErrorCode.USER_NOT_FOUND.toString());

		user.setUsername(userBean.getUsername());
		user.setEmailAddress(userBean.getEmailAddress());
		user.setPassword(passwordService.encryptPassword(userBean.getPassword()));
		user.setRoles(Lists.newArrayList(role));
		Optional<String> companyName = Optional.ofNullable(userBean.getCompanyName());
		companyName.ifPresent(c -> user.setCompany(new Company(c)));


//		role.setPermissions(Lists.newArrayList(permission));

		System.out.println("SEE PERMISSION");
		for(Permission p : role.getPermissions()){
			System.out.println(p.getName().toString());
		}

		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public void activateUser(long id) {
		User user = userRepository.findOne(id);
		Preconditions.checkState(user != null, "[activateUser Error]: User with id %s not found.", id);
		user.setToken(null);
	}

	@Override
	public User findById(long id) {
		return null;
	}

	@Override
	public User findByUuid(String uuid) {
		return userRepository.findByUuid(uuid);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmailAddress(String emailAddress) {
		return userRepository.findByEmailAddress(emailAddress);
	}

	@Override
	public User findByToken(String token) {
		return userRepository.findByToken(token);
	}

}
