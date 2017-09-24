package com.caldevsupplychain.account.dto;

import com.caldevsupplychain.common.type.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignupDTO implements Serializable {

    private static final long serialVersionUID = -8590751314752005181L;
    private String username;
    private String emailAddress;
    private String password;
}
