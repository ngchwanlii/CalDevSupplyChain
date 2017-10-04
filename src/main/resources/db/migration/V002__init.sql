CREATE TABLE IF NOT EXISTS email_templates (
  id BIGINT AUTO_INCREMENT NOT NULL,
  created_on TIMESTAMP,
  last_modified TIMESTAMP,
  locale VARCHAR(100) NOT NULL,
  subject VARCHAR(255) NOT NULL,
  to_email VARCHAR(255),
  from_email VARCHAR(255),
  content TEXT NOT NULL,
  type VARCHAR(100) UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO email_templates (created_on, last_modified, locale, subject, to_email, from_email, content, type)
  VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'en_US', 'Registration Activation Link', NULL, NULL, '<a href=http://localhost:8080/api/account/v1/activate/{token}>Activate Account Confirmation Link', 'REGISTRATION');
