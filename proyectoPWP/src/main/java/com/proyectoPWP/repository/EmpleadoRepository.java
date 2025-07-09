package com.proyectoPWP.repository;

import com.proyectoPWP.domain.empleado.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    java.util.Optional<Empleado> findByCodigo(String codigo);
}
