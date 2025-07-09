
DROP DATABASE IF EXISTS fidegym;

CREATE DATABASE fidegym CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE fidegym;

CREATE USER IF NOT EXISTS 'fidegym'@'%' IDENTIFIED BY 'fidegym';
GRANT ALL PRIVILEGES ON fidegym.* TO 'fidegym'@'%';
FLUSH PRIVILEGES;

CREATE TABLE usuarios (
    id_usuario              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre                  VARCHAR(255) NOT NULL,
    apellido                VARCHAR(255) NOT NULL,
    email                   VARCHAR(255) NOT NULL UNIQUE,
    telefono                VARCHAR(255),
    genero                  VARCHAR(50),
    contrasena              VARCHAR(255) NOT NULL,
    objetivo_entrenamiento  VARCHAR(255),
    fecha_nacimiento        DATE,
    fecha_registro          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo                  BOOLEAN NOT NULL DEFAULT TRUE,
    tipo_usuario            VARCHAR(31) NOT NULL,
    codigo                  VARCHAR(20) UNIQUE,
    rol                     VARCHAR(100),
    especialidad            VARCHAR(255),
    anios_experiencia       INT,
    direccion               VARCHAR(255),
    cedula                  VARCHAR(255),
    departamento            VARCHAR(255)
);

CREATE TABLE sedes (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(255) NOT NULL,
    direccion   VARCHAR(255) NOT NULL,
    telefono    VARCHAR(100)
);

CREATE TABLE ejercicios (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(255) NOT NULL,
    grupo_muscular  VARCHAR(255),
    video_url       VARCHAR(500)
);

CREATE TABLE rutinas (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(255) NOT NULL,
    nivel           VARCHAR(100),
    duracion_min    INT,
    descripcion     VARCHAR(500)
);

CREATE TABLE planes (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(255) NOT NULL,
    descripcion     VARCHAR(500),
    duracion_meses  INT,
    precio          INT
);

CREATE TABLE ofertas (
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre                VARCHAR(255) NOT NULL,
    porcentaje_descuento  INT NOT NULL,
    activo                BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_fin             DATE
);

CREATE TABLE clases (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(255) NOT NULL,
    descripcion     VARCHAR(500),
    horario         VARCHAR(255),
    cupo_max        INT
);

INSERT INTO sedes (nombre, direccion, telefono) VALUES
  ('Sede Central', 'Av. Principal 123', '8513-0420'),
  ('Sede Norte', 'Calle 45 N° 67', '8513-0420');

INSERT INTO planes (nombre, descripcion, duracion_meses, precio) VALUES
  ('Plan Básico', 'Acceso ilimitado a la sala de máquinas', 1, 30),
  ('Plan Premium', 'Incluye clases dirigidas y entrenador personal', 3, 75);

INSERT INTO ofertas (nombre, porcentaje_descuento, activo, fecha_fin) VALUES
  ('Promo Verano', 20, TRUE, '2025-09-30');

INSERT INTO ejercicios (nombre, grupo_muscular, video_url) VALUES
  ('Press de banca', 'Pectoral', 'aunNoTenemosHechoLoDeFirebase'),
  ('Sentadilla', 'Piernas', 'aunNoTenemosHechoLoDeFirebase');

INSERT INTO rutinas (nombre, nivel, duracion_min, descripcion) VALUES
  ('Rutina Principiantes', 'Básico', 45, 'Rutina general para iniciarse en el gimnasio');

INSERT INTO clases (nombre, descripcion, horario, cupo_max) VALUES
  ('Yoga mañanas', 'Clase suave de yoga', 'Lu-Mi-Vi 07:00', 20);


INSERT INTO usuarios (nombre, apellido, email, contrasena, tipo_usuario, direccion, cedula)
VALUES ('Juan', 'Pérez', 'juan@Juan.com', '1234', 'CLIENTE', 'Calle 1', '100100100');

INSERT INTO usuarios (nombre, apellido, email, contrasena, tipo_usuario, codigo, rol, departamento)
VALUES ('Ana', 'López', 'ana.admin@fidegym.com', 'admin123', 'ADMIN', 'ADM-00001', 'ADMIN', 'Finanzas');

INSERT INTO usuarios (nombre, apellido, email, contrasena, tipo_usuario, codigo, rol, especialidad)
VALUES ('Carlos', 'Ramírez', 'carlos.ent@fidegym.com', 'ent123', 'ENTRENADOR', 'ENT-00001', 'ENTRENADOR', 'Cardio');

    
