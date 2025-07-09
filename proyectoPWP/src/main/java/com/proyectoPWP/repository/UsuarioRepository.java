package com.proyectoPWP.repository;

import com.proyectoPWP.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.codigo = :codigo")
    Optional<Usuario> findByCodigo(@Param("codigo") String codigo);
}
