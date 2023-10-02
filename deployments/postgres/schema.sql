CREATE TABLE link
(
  id BIGSERIAL CONSTRAINT pk_link PRIMARY KEY ,
  code VARCHAR(32) NOT NULL CONSTRAINT uk_link_code UNIQUE,
  url VARCHAR(255) NOT NULL ,
  created_datetime timestamptz NOT NULL
);