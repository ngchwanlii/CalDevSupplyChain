CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT NOT NULL,
  uuid VARCHAR(36) NOT NULL UNIQUE,
  created_on TIMESTAMP NOT NULL,
  last_modified TIMESTAMP,
  username VARCHAR(255),
  email_address VARCHAR(255) NOT NULL,
  token VARCHAR(36),
  password VARCHAR(255),
  company_id BIGINT,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX users_idx01 ON users (email_address);

CREATE TABLE IF NOT EXISTS roles (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(36) UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS permissions (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(36) UNICODE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS role_permission (
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX role_permission_idx01 ON role_permission (role_id, permission_id);

CREATE TABLE IF NOT EXISTS user_2_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX user_2_role_idx01 ON user_2_role (user_id, role_id);

INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('AGENT');
INSERT INTO roles (name) VALUES ('ADMIN');
