# //TODO fix it
 CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  first_name VARCHAR(45) NULL,
  last_name VARCHAR(45) NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX email_UNIQUE (email ASC),
  UNIQUE INDEX login_UNIQUE (login ASC));

  CREATE TABLE role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NULL,
  PRIMARY KEY (id));

  CREATE TABLE permission (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NULL,
  PRIMARY KEY (id));

CREATE TABLE role_user_mapping (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  INDEX fk_role_idx (role_id ASC),
  CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_role
    FOREIGN KEY (role_id)
    REFERENCES role (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE permission_user_mapping (
  permission_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (permission_id, user_id),
  INDEX fk_user_idx (user_id ASC),
  CONSTRAINT fk_permission
    FOREIGN KEY (permission_id)
    REFERENCES permission (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_user_fk
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE address (
  id INT NOT NULL,
  address_line VARCHAR(45) NOT NULL,
  country VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));









