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
    
    // Contar usuarios activos
    long countByActivoTrue();
    
    // Contar nuevos usuarios este mes
    @Query(value = "SELECT COUNT(*) FROM usuarios WHERE MONTH(fecha_registro) = MONTH(CURRENT_DATE()) AND YEAR(fecha_registro) = YEAR(CURRENT_DATE())", nativeQuery = true)
    long countNuevosUsuariosEsteMes();
    
    // Contar usuarios por tipo
    @Query(value = "SELECT COUNT(*) FROM usuarios WHERE tipo_usuario = ?1", nativeQuery = true)
    long countByTipoUsuario(String tipoUsuario);
}
