package com.caldevsupplychain.notification.mail.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
public class MailConfig {

	@Autowired
	Environment env;

	@Bean
	public JavaMailSender javaMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername(env.getProperty("ADMIN_EMAIL_ADDRESS"));
		mailSender.setPassword(env.getProperty("ADMIN_EMAIL_PASSWORD"));
		mailSender.setPort(587);

		Properties properties = new Properties();
		// for DEBUG
//        properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		mailSender.setJavaMailProperties(properties);

		return mailSender;
	}

}
