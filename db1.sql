CREATE DATABASE IF NOT EXISTS`MERCADITO13` character set UTF8 collate utf8_bin;
USE MERCADITO13;

CREATE TABLE cliente
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, rut varchar(255) NOT NULL, nombre varchar(255) NOT NULL);

CREATE TABLE proveedor
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, rut varchar(255) NOT NULL, nombre varchar(255) NOT NULL);

CREATE TABLE producto
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, cod varchar(255) NOT NULL, nombre varchar(255) NOT NULL, descr LONGTEXT, proveedor_id bigint NOT NULL, 
FOREIGN KEY (proveedor_id) REFERENCES proveedor(id));

CREATE TABLE articulo
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, cod varchar(255) NOT NULL, nombre varchar(255) NOT NULL, producto_id bigint NOT NULL, 
FOREIGN KEY (producto_id) REFERENCES producto(id));

CREATE TABLE caja
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, cliente_id bigint NOT NULL, 
FOREIGN KEY (cliente_id) REFERENCES cliente(id));

CREATE TABLE cajadetalle
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, cantidad int NOT NULL, caja_id bigint NOT NULL, producto_id bigint NOT NULL,
FOREIGN KEY (caja_id) REFERENCES caja(id), FOREIGN KEY (producto_id) REFERENCES producto(id));

CREATE TABLE venta
(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, total int NOT NULL, cliente_id bigint NOT NULL, caja_id bigint NOT NULL,
FOREIGN KEY (cliente_id) REFERENCES cliente(id), FOREIGN KEY (caja_id) REFERENCES caja(id));
