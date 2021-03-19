DROP TABLE IF EXISTS PERSON;

CREATE TABLE PERSON (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  age INT DEFAULT NULL
);

INSERT INTO PERSON (first_name, last_name, age) VALUES
  ('Aliko', 'Dangote', 70),
  ('Bill', 'Gates', 60),
  ('Folrunsho', 'Alakija', 55);