CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL
    CONSTRAINT pk__users_id PRIMARY KEY,

  username VARCHAR
    CONSTRAINT uk__users_username UNIQUE
    CONSTRAINT ch__users_username CHECK (username ~ '[\w\d]+')
    CONSTRAINT nn__users_username NOT NULL,

  email VARCHAR
    CONSTRAINT uk__users_email UNIQUE
    CONSTRAINT ch__users_email CHECK (email ~ '')
    CONSTRAINT nn__users_email NOT NULL,

  password VARCHAR
    CONSTRAINT ch__users_password CHECK (password ~ '[\w\d]{6,255}')
    CONSTRAINT nn__users_password NOT NULL,

  score BIGINT
    CONSTRAINT ch__users_score CHECK (score >= 0)
    CONSTRAINT nn__users_score NOT NULL
  DEFAULT 0
);