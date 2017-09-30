package com.caldevsupplychain.common.ws.account;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWS implements Serializable {

	private static final long serialVersionUID = 1110144298076663428L;

	private String uuid;
	private String username;
	private String emailAddress;
	private String password;
	private String role;
	private String companyName;
	private Date createdOn;
	private Date lastModified;

}

