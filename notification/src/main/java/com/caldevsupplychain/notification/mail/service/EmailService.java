package com.caldevsupplychain.notification.mail.service;

import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface EmailService {
	void sendRegistrationVerificationTokenEmail(String emailAddress, String token, HttpServletRequest request) throws MessagingException;
}
