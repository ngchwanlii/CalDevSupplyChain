package com.caldevsupplychain.account.controller;


import com.caldevsupplychain.common.ws.account.UserWS;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;


public interface AccountController {

	ResponseEntity<?> signup (String role, UserWS userWS, HttpServletRequest request, BindingResult errors);

	ResponseEntity<?> activateAccount(String token);

	ResponseEntity<?> login(UserWS userWS, BindingResult errors);

	ResponseEntity<?> logout();

	ResponseEntity<?> updateUser(String uuid, String role, UserWS userWS, BindingResult errors);
}
