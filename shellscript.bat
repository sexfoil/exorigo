@echo off
"mysql" -hlocalhost -uroot -e "CREATE DATABASE IF NOT EXISTS exorigoupos;USE exorigoupos;CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, last_name VARCHAR(20) NOT NULL, first_name VARCHAR(20) NOT NULL, login VARCHAR(20) NOT NULL, password VARCHAR(20) NOT NULL);INSERT INTO users (last_name, first_name, login, password) VALUES ('TestLastName', 'TestName', 'test', 'test');"

copy target\poliakov-1.0-SNAPSHOT.war %CATALINA_HOME%\webapps
call "%CATALINA_HOME%"\bin\catalina.bat start

timeout /t 13 /nobreak

start start http://localhost:8080/poliakov-1.0-SNAPSHOT/