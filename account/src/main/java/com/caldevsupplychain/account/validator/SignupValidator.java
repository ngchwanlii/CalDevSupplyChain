package com.caldevsupplychain.account.validator;

import com.caldevsupplychain.common.type.ErrorCode;
import com.caldevsupplychain.common.validator.EmailValidator;
import com.caldevsupplychain.common.ws.account.UserWS;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SignupValidator implements Validator {

	@Autowired
	private EmailValidator emailValidator;

	@Override
	public boolean supports(Class clazz) {
		return UserWS.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UserWS userWS = (UserWS) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", ErrorCode.USERNAME_EMPTY.name(), "Please specify a username.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", ErrorCode.EMAIL_EMPTY.name(), "Please specify an email address.");
		if (StringUtils.hasText(userWS.getEmailAddress()) && !emailValidator.matchEmailPattern(userWS.getEmailAddress())) {
			errors.rejectValue("emailAddress", ErrorCode.EMAIL_INVALID.name(), "Please enter a valid email address.");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", ErrorCode.PASSWORD_EMPTY.name(), "Please specify a password.");
	}

}
