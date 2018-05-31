CREATE TABLE user_sn (
  id SERIAL PRIMARY KEY,
  login VARCHAR(50) UNIQUE NOT NULL,
  hash_password VARCHAR(100) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
--   data_birthday VARCHAR(50) NOT NULL,
  city VARCHAR(50) NOT NULL,
  e_mail VARCHAR(50) NOT NULL,
  state VARCHAR(10) NOT NULL,
  role VARCHAR(10) NOT NULL, 
  avatar VARCHAR(100)
);