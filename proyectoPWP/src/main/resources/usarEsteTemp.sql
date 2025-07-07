-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS fitgym_db;
USE fitgym_db;

-- Tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id_rol BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero ENUM('MASCULINO', 'FEMENINO', 'OTRO', 'PREFIERO_NO_DECIR') NOT NULL,
    objetivo VARCHAR(50),
    activo BOOLEAN DEFAULT TRUE,
    id_rol BIGINT NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP NULL,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de sedes
CREATE TABLE IF NOT EXISTS sedes (
    id_sede BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    horario_apertura TIME NOT NULL,
    horario_cierre TIME NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de empleados
CREATE TABLE IF NOT EXISTS empleados (
    id_empleado BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_sede BIGINT NOT NULL,
    numero_empleado VARCHAR(20) NOT NULL UNIQUE,
    fecha_contratacion DATE NOT NULL,
    salario DECIMAL(10, 2),
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_sede) REFERENCES sedes(id_sede) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de planes
CREATE TABLE IF NOT EXISTS planes (
    id_plan BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    duracion_dias INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    acceso_ilimitado BOOLEAN DEFAULT FALSE,
    acceso_24_7 BOOLEAN DEFAULT FALSE,
    incluye_entrenador BOOLEAN DEFAULT FALSE,
    incluye_clases BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de membresías
CREATE TABLE IF NOT EXISTS membresias (
    id_membresia BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_plan BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM('ACTIVA', 'PENDIENTE_PAGO', 'CANCELADA', 'VENCIDA') NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_plan) REFERENCES planes(id_plan) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de categorías de clases
CREATE TABLE IF NOT EXISTS categorias_clase (
    id_categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    icono VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de clases
CREATE TABLE IF NOT EXISTS clases (
    id_clase BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_categoria BIGINT NOT NULL,
    id_entrenador BIGINT NOT NULL,
    id_sede BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    capacidad_maxima INT NOT NULL,
    duracion_minutos INT NOT NULL,
    nivel_dificultad ENUM('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO') NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES categorias_clase(id_categoria) ON DELETE CASCADE,
    FOREIGN KEY (id_entrenador) REFERENCES empleados(id_empleado) ON DELETE CASCADE,
    FOREIGN KEY (id_sede) REFERENCES sedes(id_sede) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de horarios de clases
CREATE TABLE IF NOT EXISTS horarios_clase (
    id_horario BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_clase BIGINT NOT NULL,
    dia_semana ENUM('LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_clase) REFERENCES clases(id_clase) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de inscripciones a clases
CREATE TABLE IF NOT EXISTS inscripciones_clase (
    id_inscripcion BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_horario_clase BIGINT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    asistio BOOLEAN DEFAULT FALSE,
    calificacion TINYINT,
    comentario TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_horario_clase) REFERENCES horarios_clase(id_horario) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de rutinas de entrenamiento
CREATE TABLE IF NOT EXISTS rutinas (
    id_rutina BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_entrenador BIGINT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    nivel_dificultad ENUM('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO') NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_entrenador) REFERENCES empleados(id_empleado) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de ejercicios
CREATE TABLE IF NOT EXISTS ejercicios (
    id_ejercicio BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    tipo_musculo ENUM('PECHO', 'ESPALDA', 'PIERNAS', 'HOMBROS', 'BICEPS', 'TRICEPS', 'ABDOMEN', 'CARDIO', 'FUNCIONAL', 'OTRO') NOT NULL,
    nivel_dificultad ENUM('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO') NOT NULL,
    imagen_url VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de detalles de rutina (relación muchos a muchos entre rutinas y ejercicios)
CREATE TABLE IF NOT EXISTS detalles_rutina (
    id_detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_rutina BIGINT NOT NULL,
    id_ejercicio BIGINT NOT NULL,
    series INT NOT NULL,
    repeticiones VARCHAR(50) NOT NULL,
    descanso_segundos INT,
    notas TEXT,
    dia_semana ENUM('LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO') NOT NULL,
    orden INT NOT NULL,
    FOREIGN KEY (id_rutina) REFERENCES rutinas(id_rutina) ON DELETE CASCADE,
    FOREIGN KEY (id_ejercicio) REFERENCES ejercicios(id_ejercicio) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de seguimiento de progreso
CREATE TABLE IF NOT EXISTS progreso_usuarios (
    id_progreso BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    fecha_registro DATE NOT NULL,
    peso_kg DECIMAL(5, 2),
    altura_cm DECIMAL(5, 2),
    porcentaje_grasa DECIMAL(5, 2),
    porcentaje_musculo DECIMAL(5, 2),
    circunferencia_cintura_cm DECIMAL(5, 2),
    circunferencia_cadera_cm DECIMAL(5, 2),
    notas TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de pagos
CREATE TABLE IF NOT EXISTS pagos (
    id_pago BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_membresia BIGINT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    metodo_pago ENUM('TARJETA', 'EFECTIVO', 'TRANSFERENCIA', 'OTRO') NOT NULL,
    referencia_pago VARCHAR(100),
    estado ENUM('PENDIENTE', 'COMPLETADO', 'RECHAZADO', 'REEMBOLSADO') NOT NULL,
    notas TEXT,
    FOREIGN KEY (id_membresia) REFERENCES membresias(id_membresia) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de notificaciones
CREATE TABLE IF NOT EXISTS notificaciones (
    id_notificacion BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    leida BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM('INFO', 'ADVERTENCIA', 'URGENTE', 'PROMOCION') NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de registros de asistencia
CREATE TABLE IF NOT EXISTS asistencias (
    id_asistencia BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_sede BIGINT NOT NULL,
    fecha_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_salida TIMESTAMP NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_sede) REFERENCES sedes(id_sede) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar roles iniciales
INSERT INTO roles (nombre, descripcion) VALUES 
('ADMIN', 'Administrador del sistema con acceso completo'),
('ENTRENADOR', 'Personal de entrenamiento que puede crear rutinas y dar seguimiento a clientes'),
('USUARIO', 'Cliente regular del gimnasio');

-- Insertar categorías de clases iniciales
INSERT INTO categorias_clase (nombre, descripcion, icono) VALUES 
('Cardio', 'Clases enfocadas en ejercicios cardiovasculares', 'heart-pulse'),
('Fuerza', 'Entrenamiento con pesas para ganar fuerza muscular', 'dumbbell'),
('Flexibilidad', 'Ejercicios para mejorar la flexibilidad y movilidad', 'spa'),
('Baile', 'Clases de baile para ejercicio cardiovascular divertido', 'music'),
('Artes Marciales', 'Disciplinas de artes marciales para defensa personal y ejercicio', 'fist-raised'),
('Funcional', 'Entrenamiento funcional para mejorar el rendimiento en actividades diarias', 'running'),
('Yoga', 'Práctica de posturas físicas, ejercicios de respiración y meditación', 'om'),
('Pilates', 'Método de ejercicio físico que se centra en el control muscular', 'spa'),
('HIIT', 'Entrenamiento en intervalos de alta intensidad', 'bolt'),
('Spinning', 'Clases de ciclismo indoor', 'bicycle');

-- Insertar ejercicios básicos
INSERT INTO ejercicios (nombre, descripcion, tipo_musculo, nivel_dificultad) VALUES 
('Sentadillas', 'Ejercicio básico para piernas y glúteos', 'PIERNAS', 'PRINCIPIANTE'),
('Flexiones de pecho', 'Ejercicio para el pecho, hombros y tríceps', 'PECHO', 'PRINCIPIANTE'),
('Dominadas', 'Ejercicio para espalda y brazos', 'ESPALDA', 'INTERMEDIO'),
('Press de banca', 'Ejercicio con barra para pecho', 'PECHO', 'INTERMEDIO'),
('Peso muerto', 'Ejercicio compuesto para espalda y piernas', 'ESPALDA', 'AVANZADO'),
('Press militar', 'Ejercicio para hombros con barra', 'HOMBROS', 'INTERMEDIO'),
('Curl de bíceps', 'Ejercicio de aislamiento para bíceps', 'BICEPS', 'PRINCIPIANTE'),
('Fondos en paralelas', 'Ejercicio para tríceps y pecho', 'TRICEPS', 'INTERMEDIO'),
('Plancha abdominal', 'Ejercicio isométrico para el core', 'ABDOMEN', 'PRINCIPIANTE'),
('Burpees', 'Ejercicio cardiovascular completo', 'CARDIO', 'INTERMEDIO'),
('Zancadas', 'Ejercicio para piernas y glúteos', 'PIERNAS', 'PRINCIPIANTE'),
('Remo con barra', 'Ejercicio para espalda media y bíceps', 'ESPALDA', 'INTERMEDIO'),
('Elevaciones laterales', 'Ejercicio para hombros con mancuernas', 'HOMBROS', 'PRINCIPIANTE'),
('Fondos de tríceps', 'Ejercicio para tríceps en banco', 'TRICEPS', 'PRINCIPIANTE'),
('Abdominales crunch', 'Ejercicio básico para abdominales', 'ABDOMEN', 'PRINCIPIANTE');

-- Insertar planes de membresía básicos
INSERT INTO planes (nombre, descripcion, duracion_dias, precio, acceso_ilimitado, acceso_24_7, incluye_entrenador, incluye_clases) VALUES 
('Básico', 'Acceso a instalaciones en horario estándar', 30, 29.99, FALSE, FALSE, FALSE, FALSE),
('Estándar', 'Acceso ilimitado a instalaciones y clases en grupo', 30, 49.99, TRUE, FALSE, FALSE, TRUE),
('Premium', 'Acceso 24/7, clases ilimitadas y 2 sesiones con entrenador', 30, 79.99, TRUE, TRUE, TRUE, TRUE),
('Anual Básico', 'Membresía básica por 1 año con descuento', 365, 299.99, FALSE, FALSE, FALSE, FALSE),
('Anual Premium', 'Membresía premium por 1 año con descuento', 365, 799.99, TRUE, TRUE, TRUE, TRUE);

-- Crear usuario administrador por defecto (contraseña: admin123)
INSERT INTO usuarios (nombre, apellidos, email, password, telefono, fecha_nacimiento, genero, objetivo, activo, id_rol) 
VALUES ('Administrador', 'del Sistema', 'admin@fitgym.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1234567890', '1990-01-01', 'OTRO', 'MANTENER_FORMA', TRUE, 1);

-- Insertar una sede de ejemplo
INSERT INTO sedes (nombre, direccion, telefono, horario_apertura, horario_cierre, activa) 
VALUES ('Sede Central', 'Avenida Principal #123, Ciudad', '2222-2222', '05:00:00', '22:00:00', TRUE);

-- Insertar un entrenador de ejemplo
INSERT INTO usuarios (nombre, apellidos, email, password, telefono, fecha_nacimiento, genero, objetivo, activo, id_rol) 
VALUES ('Entrenador', 'Ejemplo', 'entrenador@fitgym.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '3333-3333', '1985-05-15', 'MASCULINO', 'GANAR_MASA_MUSCULAR', TRUE, 2);

-- Asignar el entrenador a la sede
INSERT INTO empleados (id_usuario, id_sede, numero_empleado, fecha_contratacion, salario, activo)
VALUES (2, 1, 'EMP001', '2023-01-15', 1500.00, TRUE);

-- Insertar un usuario de ejemplo
INSERT INTO usuarios (nombre, apellidos, email, password, telefono, fecha_nacimiento, genero, objetivo, activo, id_rol) 
VALUES ('Usuario', 'Ejemplo', 'usuario@fitgym.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '4444-4444', '1995-10-20', 'FEMENINO', 'BAJAR_PESO', TRUE, 3);

-- Insertar una membresía de ejemplo para el usuario
INSERT INTO membresias (id_usuario, id_plan, fecha_inicio, fecha_fin, estado)
VALUES (3, 2, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'ACTIVA');

-- Insertar un pago de ejemplo
INSERT INTO pagos (id_membresia, monto, metodo_pago, referencia_pago, estado)
VALUES (1, 49.99, 'TARJETA', 'PAY-123456', 'COMPLETADO');

-- Insertar clases de ejemplo
INSERT INTO clases (id_categoria, id_entrenador, id_sede, nombre, descripcion, capacidad_maxima, duracion_minutos, nivel_dificultad, activa)
VALUES 
(1, 1, 1, 'Cardio Intenso', 'Clase de cardio de alta intensidad', 20, 45, 'INTERMEDIO', TRUE),
(2, 1, 1, 'Entrenamiento de Fuerza', 'Levantamiento de pesas guiado', 15, 60, 'PRINCIPIANTE', TRUE),
(6, 1, 1, 'HIIT Avanzado', 'Entrenamiento por intervalos de alta intensidad', 15, 30, 'AVANZADO', TRUE);

-- Insertar horarios para las clases
INSERT INTO horarios_clase (id_clase, dia_semana, hora_inicio, hora_fin, activo)
VALUES 
(1, 'LUNES', '07:00:00', '07:45:00', TRUE),
(1, 'MIERCOLES', '07:00:00', '07:45:00', TRUE),
(1, 'VIERNES', '07:00:00', '07:45:00', TRUE),
(2, 'MARTES', '18:00:00', '19:00:00', TRUE),
(2, 'JUEVES', '18:00:00', '19:00:00', TRUE),
(3, 'SABADO', '09:00:00', '09:30:00', TRUE);