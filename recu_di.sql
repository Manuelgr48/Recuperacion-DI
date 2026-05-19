DROP DATABASE IF EXISTS recu_di;
CREATE DATABASE recu_di;
USE recu_di;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    favorite_car_id INT DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    horsepower INT NOT NULL,
    price DOUBLE NOT NULL,
    image_url VARCHAR(255) NOT NULL
    );

--Usuario Administrador por defecto (Contraseña: admin123)
INSERT INTO users (username, email, password, role) VALUES
    ('admin', 'admin@liceo.com', TO_BASE64(UNHEX(SHA2('admin123', 256))), 'ADMIN');

--.
--                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Catálogo de vehículos
INSERT INTO cars (brand, model, horsepower, price, image_url) VALUES
                                                                  ('Tesla', 'Model S', 670, 85000.0, 'https://fotos.quecochemecompro.com/tesla-model-s/tesla-model-s-rojo-dinamica.jpg?size=750x400'),
                                                                  ('Porsche', '911 Carrera', 379, 106000.0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9GCkJnJKQwhydDEKTv1ohopY2tD9Yu4KcjA&s'),
                                                                  ('Ford', 'Mustang GT', 450, 56000.0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3Rcer236-gmwrDqoi0AKECCN10STYDYxoLg&s'),
                                                                  ('Audi', 'E-tron GT', 522, 90000.0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIpY2qqYTwglhril8kcG1NTILotWWrr-k-7A&s');