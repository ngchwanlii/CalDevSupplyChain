package com.caldevsupplychain.account.service;



import com.caldevsupplychain.account.model.Company;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.common.ws.account.UserWS;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AccountService {

    boolean userExist(String emailAddress);

    User createUser(UserWS userWS);

    User updateUser(long id, String username, String emailAddress, String password, String role, Optional<String> companyName);

    User updateUsername(long id, Optional<String> username);

    User updatePassword(long id, Optional<String> password);

    User updateCompany(long id, Optional<Company> company);

    void activateUser(long id);

    Optional<User> findById(long id);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAddress(String emailAddress);

    Optional<User> findByToken(String token);



}
