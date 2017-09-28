package com.caldevsupplychain.account.controller;


import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caldevsupplychain.account.dto.UserEditDTO;
import com.caldevsupplychain.account.dto.UserLoginDTO;
import com.caldevsupplychain.account.dto.UserSignupDTO;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.service.AccountService;
import com.caldevsupplychain.account.util.AccountWSBuilder;
import com.caldevsupplychain.account.validator.EditUserValidator;
import com.caldevsupplychain.account.validator.LoginValidator;
import com.caldevsupplychain.account.validator.SignupValidator;
import com.caldevsupplychain.common.type.ErrorCode;
import com.caldevsupplychain.common.ws.account.ApiErrorsWS;
import com.caldevsupplychain.common.ws.account.UserWS;
import com.caldevsupplychain.notification.mail.service.EmailService;

@Slf4j
@RestController
@RequestMapping("/api/account/v1")
@AllArgsConstructor
public class AccountController {

	private ModelMapper modelMapper;

	private AccountService accountService;

	private AccountWSBuilder accountWSBuilder;

	/* validators */
	private SignupValidator signupValidator;
	private LoginValidator loginValidator;
	private EditUserValidator editUserValidator;

	private EmailService emailService;

	// use validator to customize error code and message
	@InitBinder("userSignupDTO")
	public void initUserSignupFormBinder(WebDataBinder binder) {binder.addValidators(signupValidator);}
	@InitBinder("userLoginDTO")
	public void initUserLoginFormBinder(WebDataBinder binder) {binder.addValidators(loginValidator);}
	@InitBinder("userEditDTO")
	public void initUserEditFormBinder(WebDataBinder binder) {binder.addValidators(editUserValidator);}



	/************************************************************************************************
	 |									Account API													|
	 ************************************************************************************************/


	@GetMapping("/demo")
	public ResponseEntity<?> demo (HttpServletRequest request) throws NullPointerException {

		throw new NullPointerException("Throwing from demo");
//        throw new IllegalAccessError("Illegal throw from dog");
//        return null;
	}


	@PostMapping("/signup")
	public ResponseEntity<?> signup (
			@RequestParam(required = false, defaultValue = "USER") String role,
			@Validated @RequestBody UserSignupDTO userSignupDTO,
			HttpServletRequest request
	) throws MessagingException
	{
		// convert back to UserWS after signup validation
		UserWS userWS = modelMapper.map(userSignupDTO, UserWS.class);

		// need to update after activated link, and user select specific role
		userWS.setRole(role);

		/*  TODO: Signup Process Thought
			if we break down the signup process to 3 parts
			1. user sign up (username, email, password) << currently this user only have USER role
			2. send email activation link
			3. user activated -> navigate to another detail signup form:
				  - where user can select role ( << after selected, frontEnd UI change to specific form for either
					Designer <> Agent <> Manufacturer to signup for more details information
			4. user complete  all the signup process:
				  - send notification to admin for review then to approve/unapprove these users before users
					can go into a specific dashboard view
		  */

		// check if user pre-exist or not
		if (accountService.userExist(userWS.getEmailAddress())) {
			return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.ACCOUNT_EXIST.name(), "Account already registered."), HttpStatus.CONFLICT);
		}

		// success on checking, create user
		User user = accountService.createUser(userWS);

		log.warn("CREATE USER SUCCESS");

		emailService.sendRegistrationVerificationTokenEmail(user.getEmailAddress(), user.getToken(), request);


		log.warn("SENDING EMAIL ACTIVATION TOKEN");

		return new ResponseEntity<>(accountWSBuilder.buildUserWS(user), HttpStatus.CREATED);
	}

	@GetMapping("/activate")
	public ResponseEntity<?> activateAccount(@PathParam("token") String token) {

		Optional<User> user =  accountService.findByToken(token);

		if(!user.isPresent()){
			return new ResponseEntity<Object>(new ApiErrorsWS(ErrorCode.INVALID_TOKEN.name(), "Invalid Token."), HttpStatus.BAD_REQUEST);
		}

		// validated token -> login user
		accountService.activateUser(user.get().getId());

		log.warn("USER ACTIVATED");

		return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody UserLoginDTO userLoginDTO) {

		Optional<User> user = accountService.findByEmailAddress(userLoginDTO.getEmailAddress());

		// first check if user account exists or not
		if(!user.isPresent()) {
			return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.LOGIN_INVALID.name(), "Invalid Login"), HttpStatus.UNAUTHORIZED);
		}

		Subject subject = SecurityUtils.getSubject();

		// Apache Shiro authentication check
		if(!subject.isAuthenticated()) {

			UsernamePasswordToken token = new UsernamePasswordToken(userLoginDTO.getEmailAddress(), userLoginDTO.getPassword(), true);

			try {
				subject.login(token);
			}
			catch (AuthenticationException e) {

				log.warn("USER LOGIN FAIL");

				return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.LOGIN_INVALID.name(), e.getMessage()), HttpStatus.UNAUTHORIZED);
			}
		}


		log.warn("USER LOGIN SUCCESS");

		// success login -> return JSON object
		return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
	}


	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		Subject subject = SecurityUtils.getSubject();

		Optional<User> user = accountService.findByUuid(subject.getPrincipal().toString());

		if(!user.isPresent()){

			log.warn("USER LOGOUT FAIL");

			return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.LOGOUT_INVALID.name(), "Logout Invalid"), HttpStatus.BAD_REQUEST);
		}

		log.warn("USER LOGOUT SUCCESS");

		// logout
		subject.logout();

		return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
	}



	@GetMapping("/users")
	public ResponseEntity<?> getUserByEmailAddress(@RequestParam String emailAddress) {
		Optional<User> user = accountService.findByEmailAddress(emailAddress);
		if (!user.isPresent()) {

			log.warn("GET USER BY EMAIL FAIL");

			return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.ACCOUNT_NOT_EXIST.name(), "Get user from email address failed."), HttpStatus.NOT_FOUND);
		}


		log.warn("GET USER BY EMAIL SUCCESS");

		return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
	}


	// TODO: not working yet, update on relational database failed
	// update as a form
	@PostMapping("/users/{uuid}")
	public ResponseEntity<?> updateUser(@PathVariable("uuid") String uuid, @RequestParam String role, @Validated @RequestBody UserEditDTO userEditDTO) {

		Optional<User> user = accountService.findByUuid(uuid);

		if (!user.isPresent()) {

			log.warn("EDIT USER FAIL");

			return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.ACCOUNT_NOT_EXIST.name(), "Cannot find account."), HttpStatus.NOT_FOUND);
		}

		User updatedUser = accountService.updateUser (
				user.get().getId(),
				userEditDTO.getUsername(),
				userEditDTO.getEmailAddress(),
				userEditDTO.getPassword(),
				role,
				Optional.ofNullable(userEditDTO.getCompanyName())
		);


		log.warn("EDIT USER SUCCESS");

		return new ResponseEntity<>(accountWSBuilder.buildUserWS(updatedUser), HttpStatus.OK);
	}






	// TODO: setup Apache shiro permission


}
