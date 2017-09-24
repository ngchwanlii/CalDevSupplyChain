package com.caldevsupplychain.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 5831445343867275248L;

    private String username;
    private String emailAddress;
    private String password;
}
