CREATE TABLE articles (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  date timestamp NOT NULL,
  status VARCHAR(20) NOT NULL
);
