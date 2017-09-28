package com.caldevsupplychain.notification.mail.service;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.caldevsupplychain.notification.mail.model.EmailTemplate;
import com.caldevsupplychain.notification.mail.type.EmailType;
import com.caldevsupplychain.notification.mail.util.EmailUtil;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

	private Environment env;
	private EmailUtil emailUtil;


	@Async
	@Override
	public void sendRegistrationVerificationTokenEmail(String emailAddress, String token, HttpServletRequest request) throws MessagingException {
		if (env.getProperty("ADMIN_EMAIL_ADDRESS") == null) {
			return;
		}
		/* set email content with EmailTemplate (choose email template here) */
		EmailTemplate emailTemplate = new EmailTemplate();
		emailTemplate.setLocale(Locale.ENGLISH);
		emailTemplate.setSubject("Email Notification Demo Test");
		emailTemplate.setToEmail(emailAddress);
		emailTemplate.setFromEmail(env.getProperty("ADMIN_EMAIL_ADDRESS"));
		emailTemplate.setContent(emailUtil.generateActivationLink(request, token));
		emailTemplate.setType(EmailType.REGISTRATION);

		emailUtil.sendMimeMessage(emailTemplate);
	}


}
