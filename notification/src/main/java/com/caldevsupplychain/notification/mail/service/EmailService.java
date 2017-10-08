package com.caldevsupplychain.notification.mail.service;

import com.caldevsupplychain.notification.mail.type.EmailType;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface EmailService {
	void sendVerificationTokenEmail(String emailAddress, String token, String type) throws MessagingException;
}
