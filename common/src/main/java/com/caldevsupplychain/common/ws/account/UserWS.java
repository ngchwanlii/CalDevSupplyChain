package com.caldevsupplychain.common.ws.account;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;



@Data
public class UserWS implements Serializable {
	private static final long serialVersionUID = 1110144298076663428L;

	private String uuid;
	private String username;
	private String emailAddress;
	private String password;
	private String role;
}

