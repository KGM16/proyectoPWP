package com.proyectoPWP.repository.usuario;

import com.proyectoPWP.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
    
    Optional<Usuario> findByEmailAndActivoTrue(String email);
}
