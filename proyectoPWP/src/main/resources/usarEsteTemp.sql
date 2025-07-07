-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS fitgym;
USE fitgym;

-- Tabla de roles de usuario
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    genero ENUM('masculino', 'femenino', 'otro'),
    contrasena VARCHAR(255) NOT NULL,
    objetivo_entrenamiento VARCHAR(100),
    newsletter BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla de sedes
CREATE TABLE sedes (
    id_sede INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(20),
    horario_apertura TIME,
    horario_cierre TIME
);

-- Tabla de planes
CREATE TABLE planes (
    id_plan INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio_mensual DECIMAL(10,2) NOT NULL,
    duracion_dias INT,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla de características de los planes
CREATE TABLE caracteristicas_plan (
    id_caracteristica INT AUTO_INCREMENT PRIMARY KEY,
    id_plan INT NOT NULL,
    descripcion TEXT NOT NULL,
    incluido BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_plan) REFERENCES planes(id_plan) ON DELETE CASCADE
);

-- Tabla de membresías
CREATE TABLE membresias (
    id_membresia INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_plan INT NOT NULL,
    id_sede INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM('activa', 'expirada', 'cancelada') DEFAULT 'activa',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_plan) REFERENCES planes(id_plan),
    FOREIGN KEY (id_sede) REFERENCES sedes(id_sede)
);

-- Tabla de entrenadores
CREATE TABLE entrenadores (
    id_entrenador INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    especialidad VARCHAR(100),
    biografia TEXT,
    certificaciones TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Tabla de clases
CREATE TABLE clases (
    id_clase INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    id_entrenador INT NOT NULL,
    id_sede INT NOT NULL,
    capacidad_max INT,
    duracion_min INT,
    nivel_dificultad ENUM('principiante', 'intermedio', 'avanzado'),
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_entrenador) REFERENCES entrenadores(id_entrenador),
    FOREIGN KEY (id_sede) REFERENCES sedes(id_sede)
);

-- Tabla de horarios de clases
CREATE TABLE horarios_clase (
    id_horario INT AUTO_INCREMENT PRIMARY KEY,
    id_clase INT NOT NULL,
    dia_semana ENUM('lunes', 'martes', 'miércoles', 'jueves', 'viernes', 'sábado', 'domingo'),
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (id_clase) REFERENCES clases(id_clase) ON DELETE CASCADE
);

-- Tabla de inscripciones a clases
CREATE TABLE inscripciones_clase (
    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_horario INT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    asistio BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_horario) REFERENCES horarios_clase(id_horario)
);

-- Tabla de rutinas
CREATE TABLE rutinas (
    id_rutina INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    id_entrenador INT,
    nivel_dificultad ENUM('principiante', 'intermedio', 'avanzado'),
    FOREIGN KEY (id_entrenador) REFERENCES entrenadores(id_entrenador)
);

-- Tabla de ejercicios
CREATE TABLE ejercicios (
    id_ejercicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    grupo_muscular VARCHAR(50),
    imagen_url VARCHAR(255)
);

-- Tabla de ejercicios por rutina
CREATE TABLE rutina_ejercicios (
    id_rutina INT,
    id_ejercicio INT,
    series INT,
    repeticiones VARCHAR(50),
    descanso_segundos INT,
    dia_semana ENUM('lunes', 'martes', 'miércoles', 'jueves', 'viernes', 'sábado', 'domingo'),
    orden INT,
    PRIMARY KEY (id_rutina, id_ejercicio, dia_semana),
    FOREIGN KEY (id_rutina) REFERENCES rutinas(id_rutina) ON DELETE CASCADE,
    FOREIGN KEY (id_ejercicio) REFERENCES ejercicios(id_ejercicio)
);

-- Tabla de asignación de rutinas a usuarios
CREATE TABLE usuario_rutinas (
    id_usuario INT,
    id_rutina INT,
    fecha_asignacion DATE NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id_usuario, id_rutina),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_rutina) REFERENCES rutinas(id_rutina)
);

-- Tabla de pagos
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_membresia INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    metodo_pago VARCHAR(50),
    estado ENUM('pendiente', 'completado', 'fallido') DEFAULT 'pendiente',
    FOREIGN KEY (id_membresia) REFERENCES membresias(id_membresia)
);

-- Insertar roles iniciales
INSERT INTO roles (nombre, descripcion) VALUES 
('admin', 'Administrador del sistema'),
('entrenador', 'Entrenador personal'),
('usuario', 'Usuario regular del gimnasio');

-- Insertar planes de ejemplo
INSERT INTO planes (nombre, descripcion, precio_mensual) VALUES 
('Básico', 'Acceso básico a instalaciones', 25000.00),
('Premium', 'Acceso a instalaciones y clases grupales', 45000.00),
('VIP', 'Acceso completo con beneficios exclusivos', 75000.00);