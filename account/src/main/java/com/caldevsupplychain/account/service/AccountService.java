package com.caldevsupplychain.account.service;



import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.common.bean.account.UserBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountService {

    boolean userExist(String emailAddress);

    /****************************************************************
     *                      User                                    *
     ****************************************************************/
    User createUser(UserBean userBean);

    User updateUser(UserBean userBean);

    void activateUser(long id);

    User findById(long id);

    User findByUuid(String uuid);

    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    User findByToken(String token);


    /****************************************************************
     *                      Role                                    *
     ****************************************************************/
//    Set<Role> findUserRole(long id);




    /****************************************************************
     *                      Permission                              *
     ****************************************************************/


}
