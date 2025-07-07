package com.proyectoPWP.repository.usuario;

import com.proyectoPWP.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su correo electrónico
     * @param email Correo electrónico del usuario a buscar
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el correo electrónico especificado
     * @param email Correo electrónico a verificar
     * @return true si existe un usuario con ese correo, false en caso contrario
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca un usuario por su correo electrónico y estado activo
     * @param email Correo electrónico del usuario
     * @return Un Optional que contiene el usuario si se encuentra y está activo, o vacío si no
     */
    Optional<Usuario> findByEmailAndActivoTrue(String email);
}
