CREATE DATABASE IF NOT EXISTS bus_pass_db;
USE bus_pass_db;

CREATE TABLE IF NOT EXISTS bus_pass (
    pass_id VARCHAR(30) PRIMARY KEY,
    reg_no VARCHAR(30) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL
);
