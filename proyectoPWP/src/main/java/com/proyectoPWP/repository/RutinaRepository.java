package com.proyectoPWP.repository;

import com.proyectoPWP.domain.cliente.Cliente;
import com.proyectoPWP.domain.entrenador.Entrenador;
import com.proyectoPWP.domain.rutina.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    
    // Buscar rutinas por cliente y estado
    List<Rutina> findByClienteAndEstado(Cliente cliente, String estado);
    
    // Buscar rutinas activas por cliente
    List<Rutina> findByClienteAndActivaTrue(Cliente cliente);
    
    // Buscar rutinas generales (no personalizadas) activas
    List<Rutina> findByEsPersonalizadaFalseAndActivaTrue();
    
    // Buscar rutinas por entrenador
    List<Rutina> findByEntrenadorAndActivaTrue(Entrenador entrenador);
    
    // Buscar rutinas por objetivo
    List<Rutina> findByObjetivoAndActivaTrue(String objetivo);
    
    // Buscar rutinas por nivel
    List<Rutina> findByNivelAndActivaTrue(String nivel);
    
    // Buscar rutinas vigentes (entre fechas)
    @Query("SELECT r FROM Rutina r WHERE r.activa = true AND r.fechaInicio <= CURRENT_DATE AND r.fechaFin >= CURRENT_DATE")
    List<Rutina> findRutinasVigentes();
    
    // Buscar rutinas por nombre (búsqueda parcial)
    List<Rutina> findByNombreContainingIgnoreCaseAndActivaTrue(String nombre);
    
    // Contar rutinas por entrenador
    long countByEntrenadorAndActivaTrue(Entrenador entrenador);
    
    // Buscar rutinas personalizadas
    List<Rutina> findByEsPersonalizadaTrueAndActivaTrue();
}
