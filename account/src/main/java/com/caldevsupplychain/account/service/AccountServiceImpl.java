package com.caldevsupplychain.account.service;

import com.caldevsupplychain.account.model.Company;
import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.repository.UserRepository;
import com.caldevsupplychain.common.type.ErrorCode;
import com.caldevsupplychain.common.ws.account.UserWS;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private UserRepository userRepository;
    private PasswordService passwordService;

    @Override
    public boolean userExist(String emailAddress) {
       return userRepository.findByEmailAddress(emailAddress) != null;
    }

    /*
        TODO: Need Calvin to check this args
        Here, I make company optional since some people (like freelancer) may or may not have company with them
      */
    @Override
    @Transactional
    public User createUser(UserWS userWS) {
         User user = new User();
         user.setUsername(userWS.getUsername());
         user.setEmailAddress(userWS.getEmailAddress());
         user.setPassword(passwordService.encryptPassword(userWS.getPassword()));
         user.setToken(UUID.randomUUID().toString());

         // TODO: Roles or CompanyName maybe in different signup flow? Ex: detailSignupForm will update these 2 properties
         user.setRoles(Sets.newHashSet(new Role(userWS.getRole())));
         Optional.ofNullable(userWS.getCompanyName()).ifPresent(c -> user.setCompany(new Company(c)));
         userRepository.save(user);
         return user;
    }

    // overall update on user info
    @Override
    @Transactional
    public User updateUser(long id, String username, String emailAddress, String password, String role, Optional<String> companyName) {
        User user = userRepository.findOne(id);
        Preconditions.checkState(user != null, ErrorCode.USER_NOT_FOUND.toString());
        user.setUsername(username);
        user.setEmailAddress(emailAddress);
        user.setPassword(passwordService.encryptPassword(password));
        user.setRoles(Sets.newHashSet(new Role(role)));

        log.warn("CANT THIS EXECUTE?");


        companyName.ifPresent(c -> user.setCompany(new Company(c)));
        userRepository.save(user);
        return user;
    }


    @Override
    @Transactional
    public User updateUsername(long id, Optional<String> username) {
        User user = userRepository.findOne(id);
        Preconditions.checkState(user != null, ErrorCode.USER_NOT_FOUND.toString());
        username.ifPresent(u -> user.setUsername(u));
        return user;
    }

    @Override
    @Transactional
    public User updatePassword(long id, Optional<String> password) {
        User user = userRepository.findOne(id);
        Preconditions.checkState(user != null, "[updatePassword Error]: User with id %s not found.", id);
        password.ifPresent(p -> user.setPassword(passwordService.encryptPassword(p)));
        return user;
    }

    @Override
    @Transactional
    public User updateCompany(long id, Optional<Company> company) {
        User user = userRepository.findOne(id);
        Preconditions.checkState(user != null, "[updateCompany Error]: User with id %s not found.", id);
        company.ifPresent(c -> user.setCompany(c));
        return user;
    }

    @Override
    @Transactional
    public void activateUser(long id) {
        User user = userRepository.findOne(id);
        Preconditions.checkState(user != null, "[activateUser Error]: User with id %s not found.", id);
        // reset user token to null after user successfully activated
        user.setToken(null);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> findByUuid(String uuid) {
        return Optional.ofNullable(userRepository.findByUuid(uuid));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return Optional.ofNullable(userRepository.findByEmailAddress(emailAddress));
    }

    @Override
    public Optional<User> findByToken(String token) {
        return Optional.ofNullable(userRepository.findByToken(token));
    }
}
