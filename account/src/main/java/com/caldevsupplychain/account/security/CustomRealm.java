package com.caldevsupplychain.account.security;

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
import com.caldevsupplychain.account.repository.UserRepository;

@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;

    public CustomRealm() {
        setName("CustomRealm");
    }
    
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        // token's principal = email address
        User user = userRepository.findByEmailAddress(token.getPrincipal().toString());

        if( user != null && passwordService.passwordsMatch(token.getPassword(), user.getPassword())) {
            // General authentication's principal: User's uuid
            return new SimpleAuthenticationInfo(user.getUuid(), user.getPassword(), getName());
        }

        return null;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String uuid = (String) principals.fromRealm(getName()).iterator().next();
        User user = userRepository.findByUuid(uuid);

        if( user != null ) {

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // build authroization info
//            user.getRoles().forEach(role -> {
//                // add user role
//                info.addRole(role.getName());
//                // convert each users' role permission to Collection<String> permissions
//                //info.addStringPermissions(role.getPermissions().stream().map(p -> p.toString()).collect(toList()) );
//            });
            return info;
        }
        return null;
    }

}
