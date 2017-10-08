package com.caldevsupplychain.account.controller;


import org.springframework.http.ResponseEntity;

import com.caldevsupplychain.common.ws.account.UserWS;


public interface AccountController {
	ResponseEntity<?> signup(String role, UserWS userWS);

	ResponseEntity<?> updateUser(String uuid, UserWS userWS);

	ResponseEntity<?> activateAccount(String token);

	ResponseEntity<?> login(UserWS userWS);

	ResponseEntity<?> logout();
}
