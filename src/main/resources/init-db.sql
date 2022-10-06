DROP DATABASE IF EXISTS test_task_db;
DROP USER IF EXISTS `test-task-admin`@`%`;
DROP USER IF EXISTS `test-task-user`@`%`;
CREATE DATABASE IF NOT EXISTS test_task_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `test-task-admin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `test_task_db`.* TO `test-task-admin`@`%`;
CREATE USER IF NOT EXISTS `test-task-user`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `test_task_db`.* TO `test-task-user`@`%`;
FLUSH PRIVILEGES;