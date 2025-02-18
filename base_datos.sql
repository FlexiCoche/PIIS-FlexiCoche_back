/*

  MYSQL_ADDON_HOST=b3qafrwxyfbblpfkvrsh-mysql.services.clever-cloud.com
  MYSQL_ADDON_DB=b3qafrwxyfbblpfkvrsh
  MYSQL_ADDON_USER=utwtwkmycpw7tuwc
  MYSQL_ADDON_PORT=3306
  MYSQL_ADDON_PASSWORD=hWROSP7eSnBsETq3xJuw
  MYSQL_ADDON_URI=mysql://utwtwkmycpw7tuwc:hWROSP7eSnBsETq3xJuw@b3qafrwxyfbblpfkvrsh-mysql.services.clever-cloud.com:3306/b3qafrwxyfbblpfkvrsh


  PRIMERA VERSIÓN TABLAS
*/
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(255) NOT NULL UNIQUE,
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    nombre VARCHAR(255),
    apellido1 VARCHAR(255),
    apellido2 VARCHAR(255),
    numero_telefono VARCHAR(20),
    direccion VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foto BLOB
);

CREATE TABLE vehiculos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    precio_por_dia DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(1) CHECK (estado IN ('R', 'A', 'D')) NOT NULL, -- (R:RESERVADO, A:ALQUILADO, D:DISPONIBLE)
    descripcion VARCHAR(500),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    imagen BLOB
);

CREATE TABLE alquileres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    vehiculo_id BIGINT,
    fecha_alquiler TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion TIMESTAMP,
    precio_total DECIMAL(10, 2),    
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id)
);




-- Insert sample data
INSERT INTO usuarios (nombre_usuario, correo_electronico, contrasena, nombre, apellido1, apellido2, numero_telefono, direccion) VALUES
('jdoe1', 'jdoe1@example.com', 'password123', 'John', 'Doe', 'Smith', '1234567890', '123 Main St'),
('asmith1', 'asmith1@example.com', 'password456', 'Alice', 'Smith', 'Johnson', '0987654321', '456 Elm St');

INSERT INTO vehiculos (tipo, marca, modelo, ano, precio_por_dia, estado, descripcion) VALUES
('Coche', 'Toyota', 'Corolla', 2020, 50.00, 'D', 'Compacto y eficiente'),
('Moto', 'Yamaha', 'MT-07', 2019, 30.00, 'A', 'Deportiva y ligera'),
('Furgoneta', 'Ford', 'Transit', 2021, 80.00, 'R', 'Espaciosa y cómoda');

INSERT INTO alquileres (usuario_id, vehiculo_id, fecha_devolucion, precio_total) VALUES
(1, 1, '2023-10-15 10:00:00', 150.00),
(2, 2, '2023-10-20 15:00:00', 90.00);
