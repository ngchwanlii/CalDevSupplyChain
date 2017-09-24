package com.caldevsupplychain.notification.mail.model;

import com.caldevsupplychain.common.entity.BaseEntity;
import com.caldevsupplychain.notification.mail.type.EmailType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Locale;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "email_templates")
public class EmailTemplate extends BaseEntity {
	@Column(name = "locale", nullable = false)
	private Locale locale;

	@Column(name = "subject")
	private String subject;

	@Column(name = "toEmail")
	private String toEmail;

	@Column(name = "fromEmail")
	private String fromEmail;

	@Column(name = "content")
	private String content;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private EmailType type;

}
