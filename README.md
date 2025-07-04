# FideGym - Sistema de Gestión de Gimnasio

FideGym es una aplicación web desarrollada en Java Spring Boot para la gestión integral de un gimnasio. Permite la administración de usuarios, empleados, clases, planes y sedes, ofreciendo diferentes interfaces según el tipo de usuario.

## Características Principales

- **Autenticación de Usuarios y Empleados**
- **Gestión de Perfiles** (Usuarios, Entrenadores, Administradores)
- **Control de Clases y Asistencias**
- **Seguimiento de Progreso**
- **Planes de Nutrición Personalizados**
- **Promociones y Membresías**
- **Gestión Multi-sedes**

## Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- MySQL 8.0+
- Node.js 16+ (para el frontend)

## Configuración del Proyecto

1. **Clonar el repositorio**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd proyectoPWP
   ```

2. **Configurar la base de datos**
   - Crear una base de datos MySQL llamada `fidegym_db`
   - Configurar las credenciales en `src/main/resources/application.properties`

3. **Ejecutar el proyecto**
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder a la aplicación**
   - Frontend: `http://localhost:8080`
   - API REST: `http://localhost:8080/api`

## Estructura del Proyecto

```
proyectoPWP/
├── src/
│   ├── main/
│   │   ├── java/com/proyectoPWP/
│   │   │   ├── FideGymApplication.java    # Punto de entrada de la aplicación
│   │   │   ├── ProjectConfig.java         # Configuración del proyecto
│   │   │   ├── controller/                # Controladores REST
│   │   │   ├── domain/                    # Modelos de dominio
│   │   │   │   ├── admin/                 # Modelos de administración
│   │   │   │   ├── empleado/              # Modelos de empleados
│   │   │   │   ├── interfaces/            # Interfaces de servicios
│   │   │   │   └── usuario/               # Modelos de usuario
│   │   │   ├── repository/                # Repositorios de datos
│   │   │   └── service/                   # Lógica de negocio
│   │   └── resources/
│   │       ├── static/                    # Archivos estáticos (CSS, JS, imágenes)
│   │       ├── templates/                 # Plantillas Thymeleaf
│   │       ├── application.properties     # Configuración de la aplicación
│   │       └── crearTablas.sql            # Scripts de base de datos
```

## Vistas Disponibles

### Autenticación
- **Login**: Inicio de sesión para usuarios
- **LoginEmpleados**: Inicio de sesión para empleados

### Usuario
- **MainPage**: Página principal del usuario
- **PerfilUsuario**: Gestión del perfil de usuario
- **ProgresoUsuario**: Seguimiento de progreso
- **PlanNutricionUsuario**: Plan de nutrición personalizado
- **RutinasUsuario**: Rutinas de ejercicio
- **ClasesUsuario**: Gestión de clases
- **ClaseGratis**: Clases gratuitas disponibles
- **PromosUsuario**: Promociones vigentes
- **PlanesUsuario**: Planes de membresía
- **SedesUsuario**: Ubicaciones disponibles

### Entrenador
- **PerfilEntrenador**: Gestión de perfil de entrenador

### Administración
- **PanelAdministrativo**: Panel de control principal
- **AdministrarUsuarios**: Gestión de usuarios
- **AdministrarEmpleados**: Gestión de empleados
- **AdministrarClases**: Gestión de clases
- **AdministrarPromos**: Gestión de promociones
- **AdministrarPlanes**: Gestión de planes
- **AdministrarSedes**: Gestión de sedes
- **PlanNutricionAdmin**: Administración de planes de nutrición

## Tecnologías Utilizadas

- **Backend**: Java 17, Spring Boot 3.x, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript, Bootstrap 5
- **Base de datos**: MySQL 8.0
- **Herramientas**: Maven, Git

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Para más información, por favor contacte al equipo de desarrollo.
