package com.caldevsupplychain.account.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caldevsupplychain.account.security.JpaRealm;

@Configuration
public class ShiroConfig {
	@Bean
	public Realm realm() {
		JpaRealm jpaRealm = new JpaRealm();
		jpaRealm.setCredentialsMatcher(credentialsMatcher());
		jpaRealm.setCachingEnabled(true);
		return jpaRealm;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		// use permissive to NOT require authentication, our controller Annotations will decide that
		chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
		return chainDefinition;
	}

	@Bean
	public CacheManager cacheManager() {
		// Caching isn't needed in this example, but we will use the MemoryConstrainedCacheManager for this example.
		return new MemoryConstrainedCacheManager();
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		SecurityUtils.setSecurityManager(securityManager);
		return securityManager;
	}

	@Bean
	public PasswordService passwordService() {
		return new DefaultPasswordService();
	}

	private PasswordMatcher credentialsMatcher() {
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(new DefaultPasswordService());
		return credentialsMatcher;
	}
}
