/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  kgomezm
 * Created: Jul 3, 2025
 */

CREATE SCHEMA test;
USE test;
-- Crear tablas
CREATE TABLE GYM (
    id_gym INT AUTO_INCREMENT PRIMARY KEY,
    total_ingresos DECIMAL(10, 2),
    total_usuarios INT,
    total_empleados INT
);

CREATE TABLE Beneficio (
    id_beneficio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Membresia (
    id_membresia INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    caracteristicas TEXT
);

CREATE TABLE MembresiaBeneficio (
    id_membresia INT,
    id_beneficio INT,
    PRIMARY KEY (id_membresia, id_beneficio),
    FOREIGN KEY (id_membresia) REFERENCES Membresia(id_membresia),
    FOREIGN KEY (id_beneficio) REFERENCES Beneficio(id_beneficio)
);

CREATE TABLE Sede (
    id_sede INT AUTO_INCREMENT PRIMARY KEY,
    id_gym INT,
    gerente VARCHAR(100),
    cantidad_empleados INT,
    ingresos_mes DECIMAL(10, 2),
    usuarios_activos INT,
    clases_activas INT,
    estado BOOLEAN,
    FOREIGN KEY (id_gym) REFERENCES GYM(id_gym)
);

CREATE TABLE Administrador (
    cedula VARCHAR(20) PRIMARY KEY,
    puesto VARCHAR(100),
    activo BOOLEAN,
    telefono VARCHAR(20),
    correo_trabajo VARCHAR(100),
    correo_personal VARCHAR(100),
    es_admin BOOLEAN
);

CREATE TABLE Empleado (
    cedula VARCHAR(20),
    id_sede INT,
    PRIMARY KEY (cedula, id_sede),
    FOREIGN KEY (cedula) REFERENCES Administrador(cedula),
    FOREIGN KEY (id_sede) REFERENCES Sede(id_sede)
);

CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido1 VARCHAR(100),
    apellido2 VARCHAR(100),
    telefono VARCHAR(20),
    tipo_membresia INT,
    estado BOOLEAN,
    fecha_registro DATE,
    foto_url VARCHAR(255),
    claseDePrueba BOOLEAN,
    FOREIGN KEY (tipo_membresia) REFERENCES Membresia(id_membresia)
);

CREATE TABLE Clase (
    id_clase INT AUTO_INCREMENT PRIMARY KEY,
    id_sede INT,
    cedula_entrenador VARCHAR(20),
    fecha DATE,
    hora VARCHAR(10),
    FOREIGN KEY (id_sede) REFERENCES Sede(id_sede),
    FOREIGN KEY (cedula_entrenador) REFERENCES Administrador(cedula)
);

CREATE TABLE PlanNutricional (
    id_plan INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    alimentos TEXT,
    calorias INT,
    tipo ENUM('desayuno', 'almuerzo', 'cena', 'merienda1', 'merienda2'),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Progreso (
    id_progreso INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    fecha DATE,
    peso DECIMAL(5, 2),
    tiempo_activo INT,
    peso_que_levanta_inicial DECIMAL(10, 2),
    peso_que_levanta_actual DECIMAL(10, 2),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Rutina (
    id_rutina INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Ejercicio (
    id_ejercicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    grupo_muscular VARCHAR(100),
    video_url VARCHAR(255),
    tiempo_descanso INT
);

CREATE TABLE RutinaEjercicio (
    id_rutina INT,
    id_ejercicio INT,
    PRIMARY KEY (id_rutina, id_ejercicio),
    FOREIGN KEY (id_rutina) REFERENCES Rutina(id_rutina),
    FOREIGN KEY (id_ejercicio) REFERENCES Ejercicio(id_ejercicio)
);

CREATE TABLE ClienteRutina (
    id_cliente INT,
    id_rutina INT,
    PRIMARY KEY (id_cliente, id_rutina),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_rutina) REFERENCES Rutina(id_rutina)
);

CREATE TABLE ClienteClase (
    id_cliente INT,
    id_clase INT,
    PRIMARY KEY (id_cliente, id_clase),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_clase) REFERENCES Clase(id_clase)
);

CREATE TABLE Descuento (
    id_descuento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    porcentaje_descuento DECIMAL(5, 2),
    activo BOOLEAN,
    codigo VARCHAR(20)
);

CREATE TABLE SedeDescuento (
    id_sede INT,
    id_descuento INT,
    PRIMARY KEY (id_sede, id_descuento),
    FOREIGN KEY (id_sede) REFERENCES Sede(id_sede),
    FOREIGN KEY (id_descuento) REFERENCES Descuento(id_descuento)
);

-- Insertar datos de ejemplo
INSERT INTO GYM (total_ingresos, total_usuarios, total_empleados) VALUES (0, 0, 0);

INSERT INTO Beneficio (nombre) VALUES
('Acceso a todas las sedes'),
('Uso de equipos de cardio y pesas'),
('Acceso a vestuarios y duchas'),
('Clases grupales ilimitadas'),
('Sesiones con entrenador personal'),
('Plan nutricional'),
('Acceso a sauna y spa'),
('Acceso a clases especiales'),
('Evaluación física mensual'),
('Descuentos en tienda'),
('Invitaciones para amigos');

INSERT INTO Membresia (nombre, caracteristicas) VALUES
('Básico', 'Acceso a todas las sedes, Uso de equipos de cardio y pesas, Acceso a vestuarios y duchas'),
('Premium', 'Clases grupales ilimitadas, Sesiones con entrenador personal, Plan nutricional'),
('VIP', 'Acceso a sauna y spa, Acceso a clases especiales, Evaluación física mensual, Descuentos en tienda, Invitaciones para amigos');

INSERT INTO MembresiaBeneficio (id_membresia, id_beneficio) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5), (2, 6),
(3, 7), (3, 8), (3, 9), (3, 10), (3, 11);

INSERT INTO Sede (id_gym, gerente, cantidad_empleados, ingresos_mes, usuarios_activos, clases_activas, estado) VALUES
(1, 'Gerente 1', 10, 50000, 200, 20, TRUE);

INSERT INTO Administrador (cedula, puesto, activo, telefono, correo_trabajo, correo_personal, es_admin) VALUES
('123456789', 'Gerente', TRUE, '1234567890', 'gerente@gym.com', 'gerente.personal@gym.com', TRUE);

INSERT INTO Empleado (cedula, id_sede) VALUES
('123456789', 1);

INSERT INTO Cliente (nombre, apellido1, apellido2, telefono, tipo_membresia, estado, fecha_registro, foto_url, claseDePrueba) VALUES
('Juan', 'Pérez', 'Gómez', '987654321', 1, TRUE, '2023-10-01', 'url_foto_juan', TRUE);

INSERT INTO Clase (id_sede, cedula_entrenador, fecha, hora) VALUES
(1, '123456789', '2023-10-10', '18:00-19:00');

INSERT INTO PlanNutricional (id_cliente, alimentos, calorias, tipo) VALUES
(1, 'Frutas, vegetales, proteínas', 2000, 'desayuno');

INSERT INTO Progreso (id_cliente, fecha, peso, tiempo_activo, peso_que_levanta_inicial, peso_que_levanta_actual) VALUES
(1, '2023-10-01', 70.5, 120, 50, 60);

INSERT INTO Rutina (nombre) VALUES
('Rutina de Fuerza');

INSERT INTO Ejercicio (nombre, grupo_muscular, video_url, tiempo_descanso) VALUES
('Press de banca', 'Pecho', 'url_video_press_banca', 60);

INSERT INTO RutinaEjercicio (id_rutina, id_ejercicio) VALUES
(1, 1);

INSERT INTO ClienteRutina (id_cliente, id_rutina) VALUES
(1, 1);

INSERT INTO ClienteClase (id_cliente, id_clase) VALUES
(1, 1);

INSERT INTO Descuento (nombre, porcentaje_descuento, activo, codigo) VALUES
('Descuento Inauguración', 10.0, TRUE, 'INAUGURACION10');

INSERT INTO SedeDescuento (id_sede, id_descuento) VALUES
(1, 1);