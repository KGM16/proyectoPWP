package com.proyectoPWP.repository;

import com.proyectoPWP.domain.ejercicio.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
}
