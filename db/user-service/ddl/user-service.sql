CREATE DATABASE user_service;

CREATE USER IF NOT EXISTS 'spring-user'@'%' IDENTIFIED  BY '111';
GRANT ALL ON user_service.* TO 'spring-user'@'%';