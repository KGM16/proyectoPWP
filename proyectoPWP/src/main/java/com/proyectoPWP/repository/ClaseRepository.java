package com.proyectoPWP.repository;

import com.proyectoPWP.domain.clase.Clase;
import com.proyectoPWP.domain.entrenador.Entrenador;
import com.proyectoPWP.domain.sede.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    
    // Buscar clases activas
    List<Clase> findByActivaTrue();
    
    // Buscar clases por entrenador
    List<Clase> findByEntrenadorAndActivaTrue(Entrenador entrenador);
    
    // Buscar clases por sede
    List<Clase> findBySedeAndActivaTrue(Sede sede);
    
    // Buscar clases por categoría
    List<Clase> findByCategoriaAndActivaTrue(String categoria);
    
    // Buscar clases por nivel
    List<Clase> findByNivelAndActivaTrue(String nivel);
    
    // Buscar clases con cupo disponible
    @Query("SELECT c FROM Clase c WHERE c.activa = true AND c.cupoActual < c.cupoMaximo")
    List<Clase> findClasesConCupoDisponible();
    
    // Buscar clases por rango de fechas
    @Query("SELECT c FROM Clase c WHERE c.activa = true AND c.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    List<Clase> findClasesPorRangoFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
    
    // Buscar clases de hoy
    @Query(value = "SELECT * FROM clases c WHERE c.activa = true AND DATE(c.fecha_hora) = CURRENT_DATE ORDER BY c.fecha_hora", nativeQuery = true)
    List<Clase> findClasesDeHoy();
    
    // Buscar clases por nombre (búsqueda parcial)
    List<Clase> findByNombreContainingIgnoreCaseAndActivaTrue(String nombre);
    
    // Contar clases por entrenador
    long countByEntrenadorAndActivaTrue(Entrenador entrenador);
    
    // Buscar clases próximas (siguientes 7 días)
    @Query(value = "SELECT * FROM clases c WHERE c.activa = true AND c.fecha_hora BETWEEN CURRENT_TIMESTAMP AND DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 7 DAY) ORDER BY c.fecha_hora", nativeQuery = true)
    List<Clase> findClasesProximas();
    
    // Contar clases de hoy
    @Query(value = "SELECT COUNT(*) FROM clases c WHERE c.activa = true AND DATE(c.fecha_hora) = CURRENT_DATE", nativeQuery = true)
    long countClasesHoy();
}
