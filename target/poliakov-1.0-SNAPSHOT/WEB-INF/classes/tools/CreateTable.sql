CREATE DATABASE exorigo;

USE exorigo;

CREATE TABLE users;

CREATE TABLE users (
	id 			INT AUTO_INCREMENT PRIMARY KEY, 
	last_name 	VARCHAR(20) NOT NULL, 
	first_name 	VARCHAR(20) NOT NULL, 
	login 		VARCHAR(20) NOT NULL, 
	password 	VARCHAR(20) NOT NULL
);