package com.caldevsupplychain.common.ws.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserWS implements Serializable {
	private static final long serialVersionUID = 1110144298076663428L;

	private String uuid;
	private String username;
	private String emailAddress;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private List<String> roles;
}

