CREATE DATABASE if not exists testing ;
USE testing;

DROP TABLE userstests;
DROP TABLE answers;;
DROP TABLE questions;
DROP TABLE tests;
DROP TABLE subjects;
DROP TABLE users;
DROP TABLE statuses;
DROP TABLE roles;

CREATE TABLE roles(

	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE,
	description VARCHAR(1000)
);

INSERT INTO roles VALUES(0, 'admin',null);
INSERT INTO roles VALUES(1, 'student',null);

CREATE TABLE statuses(

	id INTEGER NOT NULL unique auto_increment  PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO statuses VALUES(DEFAULT, 'banned');
INSERT INTO statuses VALUES(DEFAULT, 'active');

CREATE TABLE users(

	id INTEGER NOT NULL unique auto_increment PRIMARY KEY,
	login VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	test_results INTEGER NOT NULL DEFAULT 0,
	tests_count INTEGER NOT NULL DEFAULT 0,
	role_id INTEGER NOT NULL REFERENCES roles(id)
		ON DELETE CASCADE 
		ON UPDATE RESTRICT,
	status_id INTEGER NOT NULL REFERENCES statuses(id)
		ON DELETE CASCADE 
		ON UPDATE RESTRICT,
	email VARCHAR(50)
);

INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Denys', 'Bieliaiev', DEFAULT,DEFAULT, 0,2,NULL);

CREATE TABLE subjects(

	id INTEGER NOT NULL unique auto_increment PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tests(

	id INTEGER NOT NULL unique auto_increment  PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	test_time BIGINT NOT NULL,
	complexity INTEGER NOT NULL,
	subject_id INTEGER NOT NULL REFERENCES subjects(id) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);

CREATE TABLE questions(

	id INTEGER NOT NULL,
	text VARCHAR(1000) NOT NULL,
	test_id INTEGER NOT NULL REFERENCES tests(id)
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);

CREATE TABLE answers(

	id INTEGER NOT NULL,
	text VARCHAR(1000) NOT NULL,
	correct BOOLEAN NOT NULL,
	test_id INTEGER NOT NULL REFERENCES tests(id)
		ON DELETE CASCADE 
		ON UPDATE RESTRICT,
	question_id INTEGER NOT NULL
);

CREATE TABLE userstests(

	id INTEGER NOT NULL unique auto_increment PRIMARY KEY,
	result INTEGER NOT NULL,
	test_date TIMESTAMP NOT NULL,
	user_id INTEGER NOT NULL REFERENCES users(id)
		ON DELETE CASCADE 
		ON UPDATE RESTRICT,
	test_id INTEGER NOT NULL REFERENCES tests(id)
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);

SELECT * FROM users;
SELECT * FROM roles;

SELECT u.*, r.name FROM users u JOIN roles r ON u.role_id = r.id AND r.name = 'admin' WHERE u.id=1;
