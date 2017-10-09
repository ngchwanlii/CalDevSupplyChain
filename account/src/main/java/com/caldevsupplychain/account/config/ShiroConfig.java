package com.caldevsupplychain.account.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caldevsupplychain.account.security.JpaRealm;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableAutoConfiguration
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
		chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
		return chainDefinition;
	}

	@Bean
	public CacheManager cacheManager() {
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

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daapc = new DefaultAdvisorAutoProxyCreator();
		daapc.setProxyTargetClass(true);
		return daapc;
	}
}
