package com.caldevsupplychain.account.validator;

import com.caldevsupplychain.account.dto.UserLoginDTO;
import com.caldevsupplychain.common.type.ErrorCode;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class LoginValidator implements Validator {

    private final static String EMAIL_REGEX = "[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    // more efficient: refer to java doc
    private Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean supports(Class clazz) {
        return UserLoginDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors){
        UserLoginDTO userDTO = (UserLoginDTO) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", ErrorCode.EMAIL_EMPTY.name(), "Please specify an email address.");
        if(StringUtils.hasText(userDTO.getEmailAddress()) && !emailPattern.matcher(userDTO.getEmailAddress()).matches()){
            errors.rejectValue( "emailAddress", ErrorCode.EMAIL_INVALID.name(), "Please enter a valid email address." );
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", ErrorCode.PASSWORD_EMPTY.name(), "Please specify a password.");
    }

}
