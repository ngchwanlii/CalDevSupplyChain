package com.caldevsupplychain.account.controller;

import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.service.AccountService;
import com.caldevsupplychain.account.util.AccountWSBuilder;
import com.caldevsupplychain.account.validator.EditUserValidator;
import com.caldevsupplychain.account.validator.LoginValidator;
import com.caldevsupplychain.account.validator.SignupValidator;
import com.caldevsupplychain.common.bean.account.UserBean;
import com.caldevsupplychain.common.exception.FormErrorsExceptionHandler;
import com.caldevsupplychain.common.type.ErrorCode;
import com.caldevsupplychain.common.ws.account.ApiErrorsWS;
import com.caldevsupplychain.common.ws.account.ErrorWS;
import com.caldevsupplychain.common.ws.account.UserWS;
import com.caldevsupplychain.notification.mail.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;



@Slf4j
@RestController
@RequestMapping("/api/account/v1")
@AllArgsConstructor
public class AccountControllerImpl implements AccountController {

    private AccountService accountService;

    private AccountWSBuilder accountWSBuilder;

    /* validators */
    private SignupValidator signupValidator;
    private LoginValidator loginValidator;
    private EditUserValidator editUserValidator;

    private EmailService emailService;

    /* exception handler for form fields and bind field errors together */
    private FormErrorsExceptionHandler formErrorsExceptionHandler;


    /************************************************************************************************
     |									Account API													|
     ************************************************************************************************/

    @PostMapping("/signup")
    public ResponseEntity<?> signup (
            @RequestParam(required = false, defaultValue = "USER") String role,
            @Validated @RequestBody UserWS userWS,
            HttpServletRequest request,
            BindingResult errors
    )
    {
        // signup form validation
        signupValidator.validate(userWS, errors);

        if(errors.hasErrors()){
            log.error("ERROR IN SIGNUP VALIDATION");
            List<ErrorWS> errorWSList = formErrorsExceptionHandler.generateErrorWSList(errors);
            return new ResponseEntity<>(new ApiErrorsWS(errorWSList), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // need to update after activated link, and user select specific role
        userWS.setRole(role);

        // check if user pre-exist or not
        if (accountService.userExist(userWS.getEmailAddress())) {
            return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.ACCOUNT_EXIST.name(), "Account already registered."), HttpStatus.CONFLICT);
        }

        // convert userWS to userBean
        UserBean userBean = new UserBean(userWS);

        // success on checking, create user
        User user = accountService.createUser(userBean);

        try {
            emailService.sendRegistrationVerificationTokenEmail(user.getEmailAddress(), user.getToken(), request);
        } catch (MessagingException e) {
            return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.EMAIL_MESSAGING_EXCEPTION.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accountWSBuilder.buildUserWS(user), HttpStatus.CREATED);
    }

    // update as a form
    @RequiresPermissions("account:update_user")
    @PostMapping("/users/{uuid}")
    public ResponseEntity<?> updateUser(
            @PathVariable("uuid") String uuid,
            @RequestParam String role,
            @Validated @RequestBody UserWS userWS,
            BindingResult errors
    )
    {

        // signup form validation
        userWS.setRole(role);

        editUserValidator.validate(userWS, errors);

        if(errors.hasErrors()){
            log.error("ERROR IN EDIT USER VALIDATION");
            List<ErrorWS> errorWSList = formErrorsExceptionHandler.generateErrorWSList(errors);
            return new ResponseEntity<>(new ApiErrorsWS(errorWSList), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<User> user = Optional.ofNullable(accountService.findByUuid(uuid));


        if (!user.isPresent()) {
            log.error("ERROR IN EDIT USER");
            return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.ACCOUNT_NOT_EXIST.name(), "Cannot find account."), HttpStatus.NOT_FOUND);
        }

        // convert userWS to userBean
        userWS.setRole(role);
        UserBean userBean = new UserBean(userWS);

        User updatedUser = accountService.updateUser(userBean);

        log.info("EDIT USER SUCCESS");

        return new ResponseEntity<>(accountWSBuilder.buildUserWS(updatedUser), HttpStatus.OK);
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<?> activateAccount(@PathVariable("token") String token) {

        Optional<User> user = Optional.ofNullable(accountService.findByToken(token));

        if(!user.isPresent()){
            return new ResponseEntity<Object>(new ApiErrorsWS(ErrorCode.INVALID_TOKEN.name(), "Invalid Token."), HttpStatus.BAD_REQUEST);
        }
        accountService.activateUser(user.get().getId());

        return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserWS userWS, BindingResult errors) {

        // signup form validation
        loginValidator.validate(userWS, errors);

        if(errors.hasErrors()){
            log.error("ERROR IN LOGIN VALIDATION");
            List<ErrorWS> errorWSList = formErrorsExceptionHandler.generateErrorWSList(errors);
            return new ResponseEntity<>(new ApiErrorsWS(errorWSList), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<User> user = Optional.ofNullable(accountService.findByEmailAddress(userWS.getEmailAddress()));

        // first check if user account exists or not
        if(!user.isPresent()) {
            return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.LOGIN_INVALID.name(), "Invalid Login"), HttpStatus.UNAUTHORIZED);
        }

        Subject subject = SecurityUtils.getSubject();

        // Apache Shiro authentication check
        if(!subject.isAuthenticated()) {

            UsernamePasswordToken token = new UsernamePasswordToken(userWS.getEmailAddress(), userWS.getPassword(), true);

            try {
                subject.login(token);
            }
            catch (AuthenticationException e) {
                log.error("ERROR IN USER LOGIN");
                return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.LOGIN_INVALID.name(), e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        }
        // success login -> return JSON object
        return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Subject subject = SecurityUtils.getSubject();

        Optional<User> user = Optional.ofNullable(accountService.findByUuid(subject.getPrincipal().toString()));

        if(!user.isPresent()){
            log.error("ERROR IN USER LOGOUT");
            return new ResponseEntity<>(new ApiErrorsWS(ErrorCode.LOGOUT_INVALID.name(), "Logout Invalid"), HttpStatus.BAD_REQUEST);
        }

        log.info("USER LOGOUT SUCCESS");

        // logout
        subject.logout();

        return new ResponseEntity<>(accountWSBuilder.buildUserWS(user.get()), HttpStatus.OK);
    }

}





