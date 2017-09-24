package com.caldevsupplychain.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEditDTO implements Serializable {

    private static final long serialVersionUID = -7287203105980897879L;

    private String username;
    private String emailAddress;
    private String password;
    private String companyName;

}
