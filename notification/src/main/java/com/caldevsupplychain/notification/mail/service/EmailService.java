package com.caldevsupplychain.notification.mail.service;

import javax.mail.MessagingException;

public interface EmailService {
	void sendVerificationTokenEmail(String emailAddress, String token, String type) throws MessagingException;
}
