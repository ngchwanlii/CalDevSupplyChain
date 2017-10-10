package com.caldevsupplychain.account.vo;


import java.util.List;

import lombok.Data;

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
