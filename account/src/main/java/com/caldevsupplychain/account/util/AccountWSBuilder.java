package com.caldevsupplychain.account.util;


import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.common.ws.account.UserWS;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public class AccountWSBuilder {
    public UserWS buildUserWS(User user) {
        UserWS userWS = new UserWS();
        userWS.setUuid(user.getUuid());
        userWS.setUsername(user.getUsername());
        userWS.setEmailAddress(user.getEmailAddress());
        userWS.setCreatedOn(user.getCreatedOn());
        userWS.setLastModified(user.getLastModified());
        return userWS;
    }
}
