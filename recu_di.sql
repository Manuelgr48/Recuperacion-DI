CREATE DATABASE IF NOT EXISTS recu_di;
USE recu_di_cars;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER',
    favorite_car_id INT DEFAULT NULL
    );

INSERT INTO users (username, email, password, role)
VALUES ('admin', 'admin@liceolapaz.com', 'admin123', 'ADMIN');