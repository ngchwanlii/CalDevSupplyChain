package com.caldevsupplychain.account.security;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.service.AccountService;
import com.caldevsupplychain.account.util.UserMapper;
import com.caldevsupplychain.account.vo.UserBean;

@Slf4j
@Component
public class JpaRealm extends AuthorizingRealm  {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private UserMapper userMapper;

	public JpaRealm() {
		setName("jpaRealm");
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		UserBean user = accountService.findByEmailAddress(token.getUsername()).orElse(null);

		if (user != null && passwordService.passwordsMatch(token.getPassword(), user.getPassword())) {
			return new SimpleAuthenticationInfo(user.getUuid(), user.getPassword(), getName());
		}

		return null;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String uuid = (String) principals.fromRealm(getName()).iterator().next();
		UserBean userBean = accountService.findByUuid(uuid).orElse(null);

		User user = userMapper.MAPPER.userBeanToUser(userBean);

		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			user.getRoles().stream().distinct().forEach(role -> {
				info.addRole(role.getName().toString());
				info.addStringPermissions(role.getPermissions().stream().distinct().map(p -> p.getName().toString()).collect(Collectors.toList()));
			});
			return info;
		}
		return null;
	}


}
