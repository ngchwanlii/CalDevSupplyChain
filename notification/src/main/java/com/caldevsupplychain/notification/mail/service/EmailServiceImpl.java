package com.caldevsupplychain.notification.mail.service;

import com.caldevsupplychain.notification.mail.model.EmailTemplate;
import com.caldevsupplychain.notification.mail.repository.EmailTemplateRepository;
import com.caldevsupplychain.notification.mail.type.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@PropertySource("classpath:link.properties")
public class EmailServiceImpl implements EmailService {

	private final String TOKEN_PLACEHOLDER = "{token}";

	@Autowired
	private Environment env;
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Autowired
	private JavaMailSender javaMailSender;


	@Async
	@Override
	public void sendVerificationTokenEmail(String targetEmailAddress, String token, String type) throws MessagingException {

		if (env.getProperty("ADMIN_EMAIL_ADDRESS") == null) {
			return;
		}

		EmailTemplate emailTemplate = emailTemplateRepository.findByType(EmailType.valueOf(type));
		emailTemplate.setToEmail(targetEmailAddress);
		emailTemplate.setFromEmail(env.getProperty("ADMIN_EMAIL_ADDRESS"));
		emailTemplate.setContent(emailTemplate.getContent().replace(TOKEN_PLACEHOLDER, token));
		sendMimeMessage(emailTemplate);
	}

	public void sendMimeMessage(EmailTemplate emailTemplate) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject(emailTemplate.getSubject());
		helper.setTo(emailTemplate.getToEmail());
		helper.setFrom(emailTemplate.getFromEmail());
		helper.setText(emailTemplate.getContent(), true);
		javaMailSender.send(message);
	}

}
