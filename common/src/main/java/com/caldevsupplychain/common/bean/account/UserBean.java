package com.caldevsupplychain.common.bean.account;


import com.caldevsupplychain.common.ws.account.UserWS;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserBean {

    private String username;
    private String emailAddress;
    private String password;
    private String role;
    private String companyName;


    public UserBean(UserWS userWS) {
        this.username = userWS.getUsername();
        this.emailAddress = userWS.getEmailAddress();
        this.password = userWS.getPassword();
        this.role = userWS.getRole();
        this.companyName = userWS.getCompanyName();
    }

}
