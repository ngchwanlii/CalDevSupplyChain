package com.caldevsupplychain.account.vo;


import lombok.Data;

import java.util.List;

@Data
public class UserBean {
	private Long id;
	private String uuid;
	private String username;
	private String emailAddress;
	private String password;
	private String token;
	private List<RoleName> roles;

}
