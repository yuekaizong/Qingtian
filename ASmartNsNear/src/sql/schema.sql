CREATE TABLE uauth_user_token
(
  client_id     VARCHAR2(255 CHAR) NOT NULL
    PRIMARY KEY,
  client_secret VARCHAR2(255 CHAR),
  register_date VARCHAR2(255 CHAR),
  user_id       VARCHAR2(255 CHAR),
  device_type   VARCHAR2(255 CHAR)
)