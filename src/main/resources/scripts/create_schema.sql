CREATE DATABASE `bd_sgq` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE bd_sgq;
CREATE TABLE tb_user
(
  id bigint NOT NULL,
  active boolean,
  login character varying(255),
  password character varying(255),
  validation character varying(255),
  CONSTRAINT pk_tb_user PRIMARY KEY (id),
  CONSTRAINT uk_tb_user UNIQUE (login)
);

CREATE TABLE tb_user_permission
(
  id bigint NOT NULL,
  permission character varying(40),
  CONSTRAINT fk_tb_user_permission FOREIGN KEY (id)
      REFERENCES tb_user (id),
  CONSTRAINT uk_tb_user_permission UNIQUE (id, permission)
);

CREATE TABLE tb_profile
(
  birth timestamp,
  email character varying(255),
  firstname character varying(255),
  lastname character varying(255),
  sexy character varying(255),
  user_id bigint NOT NULL,
  CONSTRAINT pk_tb_profile PRIMARY KEY (user_id),
  CONSTRAINT fk_tb_profile FOREIGN KEY (user_id)
      REFERENCES tb_user (id),
  CONSTRAINT uk_tb_profile UNIQUE (email)
);