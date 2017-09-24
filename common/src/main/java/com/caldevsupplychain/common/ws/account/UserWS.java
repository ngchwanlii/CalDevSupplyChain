package com.caldevsupplychain.common.ws.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWS implements Serializable {

	private static final long serialVersionUID = 1110144298076663428L;

	private String uuid;
	private String username;
	private String emailAddress;
	/* password & matchedPassword logic will be checked on front-end when user key in */
	private String password;
	/* TODO: wait for Calvin to confirm on UI side */
	private String role;
	private String companyName;
	private LocalDateTime createdOn;
	private LocalDateTime lastModified;

}

