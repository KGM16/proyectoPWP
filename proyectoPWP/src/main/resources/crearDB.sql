-- =====================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS FIDEGYM
-- =====================================================

DROP DATABASE IF EXISTS fidegym;
CREATE DATABASE fidegym CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE fidegym;

-- =====================================================
-- TABLA USUARIOS (Tabla padre con herencia SINGLE_TABLE)
-- =====================================================
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_usuario VARCHAR(20) NOT NULL, -- CLIENTE, ADMIN, ENTRENADOR
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    genero VARCHAR(10),
    contrasena VARCHAR(255) NOT NULL,
    objetivo_entrenamiento VARCHAR(100),
    tipo_documento VARCHAR(10),
    numero_documento VARCHAR(50),
    peso DOUBLE,
    altura DOUBLE,
    fecha_nacimiento DATE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    
    -- Campos específicos de Empleado
    codigo VARCHAR(20) UNIQUE,
    rol VARCHAR(50),
    
    -- Campos específicos de Cliente
    cedula VARCHAR(50),
    
    -- Campos específicos de Entrenador
    especialidad VARCHAR(100),
    anios_experiencia INT,
    
    -- Campos específicos de Administrador
    departamento VARCHAR(100),
    
    INDEX idx_email (email),
    INDEX idx_tipo_usuario (tipo_usuario),
    INDEX idx_codigo (codigo)
);

-- =====================================================
-- TABLA SEDES
-- =====================================================
CREATE TABLE sedes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(150),
    ciudad VARCHAR(100),
    codigo_postal VARCHAR(20),
    descripcion TEXT,
    horario_apertura VARCHAR(10),
    horario_cierre VARCHAR(10),
    capacidad_maxima INT,
    activa BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    latitud DOUBLE,
    longitud DOUBLE,
    tiene_estacionamiento BOOLEAN DEFAULT FALSE,
    tiene_vestidores BOOLEAN DEFAULT TRUE,
    tiene_duchas BOOLEAN DEFAULT TRUE,
    tiene_wifi BOOLEAN DEFAULT FALSE,
    tiene_cafeteria BOOLEAN DEFAULT FALSE
);

-- =====================================================
-- TABLA PLANES
-- =====================================================
CREATE TABLE planes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    duracion_meses INT,
    precio INT
);

-- =====================================================
-- TABLA EJERCICIOS
-- =====================================================
CREATE TABLE ejercicios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    grupo_muscular VARCHAR(50),
    equipo_necesario VARCHAR(200),
    categoria VARCHAR(50),
    nivel VARCHAR(20),
    instrucciones TEXT,
    duracion_minutos INT,
    calorias_quemadas INT,
    url_video VARCHAR(500),
    url_imagen VARCHAR(500),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    series_recomendadas INT,
    repeticiones_recomendadas INT,
    descanso_segundos INT,
    musculos_primarios VARCHAR(500),
    musculos_secundarios VARCHAR(500)
);

-- =====================================================
-- TABLA CLASES
-- =====================================================
CREATE TABLE clases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    duracion_minutos INT,
    cupo_maximo INT,
    cupo_actual INT DEFAULT 0,
    fecha_hora TIMESTAMP,
    nivel VARCHAR(20),
    categoria VARCHAR(50),
    precio DOUBLE,
    activa BOOLEAN DEFAULT TRUE,
    entrenador_id BIGINT,
    sede_id BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (entrenador_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (sede_id) REFERENCES sedes(id),
    INDEX idx_fecha_hora (fecha_hora),
    INDEX idx_categoria (categoria),
    INDEX idx_nivel (nivel)
);

-- =====================================================
-- TABLA RUTINAS
-- =====================================================
CREATE TABLE rutinas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    objetivo VARCHAR(50),
    duracion_semanas INT,
    nivel VARCHAR(20),
    dias_por_semana INT,
    duracion_minutos INT,
    notas TEXT,
    es_personalizada BOOLEAN DEFAULT FALSE,
    cliente_id BIGINT,
    entrenador_id BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio DATE,
    fecha_fin DATE,
    calorias_estimadas INT,
    equipo_necesario VARCHAR(500),
    estado VARCHAR(20) DEFAULT 'ACTIVA',
    
    FOREIGN KEY (cliente_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (entrenador_id) REFERENCES usuarios(id_usuario),
    INDEX idx_objetivo (objetivo),
    INDEX idx_nivel (nivel),
    INDEX idx_estado (estado)
);

-- =====================================================
-- TABLA OFERTAS
-- =====================================================
CREATE TABLE ofertas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    descuento_porcentaje INT,
    descuento_fijo DOUBLE,
    fecha_inicio DATE,
    fecha_fin DATE,
    activa BOOLEAN DEFAULT TRUE,
    codigo_promocional VARCHAR(50),
    tipo_oferta VARCHAR(20),
    usos_maximos INT,
    usos_actuales INT DEFAULT 0,
    monto_minimo_compra DOUBLE,
    plan_id BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (plan_id) REFERENCES planes(id),
    INDEX idx_codigo_promocional (codigo_promocional),
    INDEX idx_fecha_vigencia (fecha_inicio, fecha_fin)
);

-- =====================================================
-- TABLAS DE RELACIÓN
-- =====================================================

-- Tabla para reservas de clases
CREATE TABLE reservas_clases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    clase_id BIGINT NOT NULL,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'CONFIRMADA', -- CONFIRMADA, CANCELADA, COMPLETADA
    
    FOREIGN KEY (cliente_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (clase_id) REFERENCES clases(id),
    UNIQUE KEY unique_reserva (cliente_id, clase_id),
    INDEX idx_estado (estado)
);

-- Tabla para ejercicios de rutinas
CREATE TABLE rutina_ejercicios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rutina_id BIGINT NOT NULL,
    ejercicio_id BIGINT NOT NULL,
    orden_ejercicio INT,
    series INT,
    repeticiones INT,
    peso_kg DOUBLE,
    descanso_segundos INT,
    notas VARCHAR(500),
    
    FOREIGN KEY (rutina_id) REFERENCES rutinas(id),
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicios(id),
    INDEX idx_rutina (rutina_id),
    INDEX idx_orden (orden_ejercicio)
);

-- Tabla para suscripciones de clientes a planes
CREATE TABLE suscripciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    precio_pagado DOUBLE,
    activa BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (cliente_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (plan_id) REFERENCES planes(id),
    INDEX idx_vigencia (fecha_inicio, fecha_fin),
    INDEX idx_cliente (cliente_id)
);

-- =====================================================
-- DATOS DE EJEMPLO
-- =====================================================

-- Insertar Administrador
INSERT INTO usuarios (tipo_usuario, nombre, apellido, email, contrasena, codigo, rol, departamento, activo) 
VALUES ('ADMIN', 'Carlos', 'Administrador', 'admin@fidegym.com', 'admin123', 'ADM001', 'ADMINISTRADOR', 'Administración', TRUE);

-- Insertar Entrenadores
INSERT INTO usuarios (tipo_usuario, nombre, apellido, email, contrasena, codigo, rol, especialidad, anios_experiencia, activo) 
VALUES 
('ENTRENADOR', 'María', 'Fitness', 'maria.fitness@fidegym.com', 'trainer123', 'ENT001', 'ENTRENADOR', 'Cardio y Resistencia', 5, TRUE),
('ENTRENADOR', 'Juan', 'Fuerza', 'juan.fuerza@fidegym.com', 'trainer123', 'ENT002', 'ENTRENADOR', 'Fuerza y Musculación', 8, TRUE),
('ENTRENADOR', 'Ana', 'Yoga', 'ana.yoga@fidegym.com', 'trainer123', 'ENT003', 'ENTRENADOR', 'Yoga y Flexibilidad', 6, TRUE);

-- Insertar Clientes de ejemplo
INSERT INTO usuarios (tipo_usuario, nombre, apellido, email, contrasena, telefono, genero, peso, altura, objetivo_entrenamiento, activo) 
VALUES 
('CLIENTE', 'Pedro', 'García', 'pedro.garcia@email.com', 'cliente123', '3001234567', 'M', 75.5, 175.0, 'PERDIDA_PESO', TRUE),
('CLIENTE', 'Laura', 'Martínez', 'laura.martinez@email.com', 'cliente123', '3007654321', 'F', 60.0, 165.0, 'TONIFICACION', TRUE),
('CLIENTE', 'Carlos', 'López', 'carlos.lopez@email.com', 'cliente123', '3009876543', 'M', 80.0, 180.0, 'GANANCIA_MUSCULAR', TRUE);

-- Insertar Sedes
INSERT INTO sedes (nombre, direccion, telefono, email, ciudad, horario_apertura, horario_cierre, capacidad_maxima, activa) 
VALUES 
('FideGYM Centro', 'Calle 50 #25-30', '6012345678', 'centro@fidegym.com', 'Bogotá', '05:00', '23:00', 200, TRUE),
('FideGYM Norte', 'Carrera 15 #85-20', '6012345679', 'norte@fidegym.com', 'Bogotá', '06:00', '22:00', 150, TRUE),
('FideGYM Sur', 'Avenida 68 #40-15', '6012345680', 'sur@fidegym.com', 'Bogotá', '05:30', '22:30', 180, TRUE);

-- Insertar Planes
INSERT INTO planes (nombre, descripcion, duracion_meses, precio) 
VALUES 
('Plan Básico', 'Acceso a gimnasio y clases grupales básicas', 1, 80000),
('Plan Premium', 'Acceso completo + entrenador personal + nutrición', 1, 150000),
('Plan Anual', 'Plan premium con descuento por pago anual', 12, 1500000),
('Plan Estudiante', 'Plan especial para estudiantes con descuento', 1, 60000);

-- Insertar Ejercicios
INSERT INTO ejercicios (nombre, descripcion, grupo_muscular, categoria, nivel, duracion_minutos, calorias_quemadas, series_recomendadas, repeticiones_recomendadas) 
VALUES 
('Sentadillas', 'Ejercicio fundamental para piernas y glúteos', 'PIERNAS', 'FUERZA', 'PRINCIPIANTE', 15, 80, 3, 12),
('Flexiones', 'Ejercicio para pecho, hombros y tríceps', 'PECHO', 'FUERZA', 'PRINCIPIANTE', 10, 60, 3, 10),
('Plancha', 'Ejercicio isométrico para core', 'CORE', 'FUNCIONAL', 'PRINCIPIANTE', 5, 30, 3, 30),
('Burpees', 'Ejercicio cardiovascular de cuerpo completo', 'CARDIO', 'CARDIO', 'INTERMEDIO', 10, 120, 3, 8),
('Peso muerto', 'Ejercicio compuesto para espalda y piernas', 'ESPALDA', 'FUERZA', 'AVANZADO', 20, 150, 4, 6);

-- Insertar Clases
INSERT INTO clases (nombre, descripcion, duracion_minutos, cupo_maximo, fecha_hora, nivel, categoria, precio, entrenador_id, sede_id, activa) 
VALUES 
('Cardio Intenso', 'Clase de cardio de alta intensidad', 45, 20, '2024-03-25 07:00:00', 'INTERMEDIO', 'CARDIO', 25000, 2, 1, TRUE),
('Yoga Matutino', 'Clase de yoga para empezar el día', 60, 15, '2024-03-25 08:00:00', 'PRINCIPIANTE', 'FLEXIBILIDAD', 20000, 4, 1, TRUE),
('Fuerza y Potencia', 'Entrenamiento de fuerza con pesas', 50, 12, '2024-03-25 18:00:00', 'AVANZADO', 'FUERZA', 30000, 3, 2, TRUE),
('Aqua Aeróbicos', 'Ejercicios en piscina de bajo impacto', 45, 25, '2024-03-25 16:00:00', 'PRINCIPIANTE', 'CARDIO', 22000, 2, 3, TRUE);

-- Insertar Rutinas
INSERT INTO rutinas (nombre, descripcion, objetivo, duracion_semanas, nivel, dias_por_semana, duracion_minutos, cliente_id, entrenador_id, estado) 
VALUES 
('Rutina Pérdida de Peso', 'Rutina enfocada en quemar grasa y mejorar condición física', 'PERDIDA_PESO', 8, 'PRINCIPIANTE', 4, 45, 5, 2, 'ACTIVA'),
('Rutina Ganancia Muscular', 'Rutina para aumentar masa muscular', 'GANANCIA_MUSCULAR', 12, 'INTERMEDIO', 5, 60, 7, 3, 'ACTIVA'),
('Rutina Tonificación', 'Rutina para tonificar y definir músculos', 'TONIFICACION', 6, 'PRINCIPIANTE', 3, 40, 6, 4, 'ACTIVA');

-- Insertar Ofertas
INSERT INTO ofertas (nombre, descripcion, descuento_porcentaje, fecha_inicio, fecha_fin, codigo_promocional, tipo_oferta, activa) 
VALUES 
('Descuento Nuevo Cliente', 'Descuento especial para nuevos miembros', 20, '2024-03-01', '2024-03-31', 'NUEVO20', 'PORCENTAJE', TRUE),
('Promo Verano', 'Oferta especial de temporada', 15, '2024-03-15', '2024-04-15', 'VERANO15', 'PORCENTAJE', TRUE),
('Black Friday', 'Descuento especial Black Friday', 30, '2024-11-29', '2024-11-29', 'BLACK30', 'PORCENTAJE', FALSE);

-- =====================================================
-- ÍNDICES ADICIONALES PARA OPTIMIZACIÓN
-- =====================================================
CREATE INDEX idx_usuarios_activo ON usuarios(activo);
CREATE INDEX idx_clases_fecha_activa ON clases(fecha_hora, activa);
CREATE INDEX idx_rutinas_cliente ON rutinas(cliente_id, estado);
CREATE INDEX idx_reservas_fecha ON reservas_clases(fecha_reserva);
CREATE INDEX idx_suscripciones_activa ON suscripciones(activa, fecha_fin);

-- =====================================================
-- SCRIPT COMPLETADO
-- =====================================================
SELECT 'Base de datos FideGYM creada exitosamente' AS mensaje;