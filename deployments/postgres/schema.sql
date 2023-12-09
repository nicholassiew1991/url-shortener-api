CREATE TABLE link
(
  id BIGSERIAL CONSTRAINT pk_link PRIMARY KEY,
  code VARCHAR(32) NOT NULL CONSTRAINT uk_link_code UNIQUE,
  url VARCHAR(255) NOT NULL ,
  created_datetime timestamptz NOT NULL
);

CREATE TABLE redirect_record
(
  id BIGSERIAL CONSTRAINT pk_redirect_record PRIMARY KEY,
  code VARCHAR(32) NOT NULL CONSTRAINT fk_redirect_record_link_code REFERENCES public.link (code),
  request_headers JSONB NOT NULL DEFAULT '{}'::JSONB,
  request_query_strings JSONB NOT NULL DEFAULT '{}'::JSONB,
  redirect_datetime TIMESTAMPTZ NOT NULL
);

