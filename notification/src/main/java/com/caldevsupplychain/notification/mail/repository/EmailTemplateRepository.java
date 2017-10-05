package com.caldevsupplychain.notification.mail.repository;

import com.caldevsupplychain.notification.mail.model.EmailTemplate;
import com.caldevsupplychain.notification.mail.type.EmailType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailTemplateRepository extends PagingAndSortingRepository<EmailTemplate, Long> {
	EmailTemplate findByType(EmailType type);
}

