CREATE DATABASE IF NOT EXISTS bus_pass_db;
USE bus_pass_db;

CREATE TABLE IF NOT EXISTS login_test (
    user_id VARCHAR(80) PRIMARY KEY,
    password_value VARCHAR(120) NOT NULL
);

INSERT INTO login_test (user_id, password_value)
VALUES ('student01', 'correct-password-for-local-demo')
ON DUPLICATE KEY UPDATE password_value = VALUES(password_value);
