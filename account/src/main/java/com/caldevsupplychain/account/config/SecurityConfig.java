package com.caldevsupplychain.account.config;


import com.caldevsupplychain.account.security.JpaRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordService passwordService(){
        return new DefaultPasswordService();
    }

    @Bean
    public Realm realm() {
        // uses 'classpath:shiro-users.properties' by default
//        PropertiesRealm realm = new PropertiesRealm();

        // set credential matcher
        PasswordMatcher credentialsMatcher = new PasswordMatcher();
        credentialsMatcher.setPasswordService(passwordService());

        // configure into realm
        JpaRealm jpaRealm = new JpaRealm();
        jpaRealm.setCredentialsMatcher(credentialsMatcher);
        jpaRealm.setCachingEnabled(true);

        return jpaRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        SecurityManager securityManager = new DefaultSecurityManager(realm());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        chainDefinition.addPathDefinition("/api/account/v1/users/**", "authc, perms[account:*]");
        chainDefinition.addPathDefinition("/api/account/v1/users/**", "authc, perms[account:update_user]");


//        /account/** = authc

        return chainDefinition;
    }


    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

}
