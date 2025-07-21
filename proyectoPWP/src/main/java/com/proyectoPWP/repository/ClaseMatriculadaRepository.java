package com.proyectoPWP.repository;

import com.proyectoPWP.domain.clase.ClaseMatriculada;
import com.proyectoPWP.domain.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaseMatriculadaRepository extends JpaRepository<ClaseMatriculada, Long> {
    
    List<ClaseMatriculada> findByUsuario(Usuario usuario);
}
