package com.proyectoPWP.service;

import com.proyectoPWP.domain.usuario.Usuario;
import com.proyectoPWP.repository.UsuarioRepository;
import com.proyectoPWP.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AutenticacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Optional<Usuario> autenticarUsuario(String email, String password) {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            return Optional.empty();
        }
        return usuarioRepository.findByEmail(email)
                .filter(u -> password.equals(u.getContrasena()) && u.isActivo());
    }

    public Optional<Usuario> autenticarEmpleado(String codigo, String password) {
        if (!StringUtils.hasText(codigo) || !StringUtils.hasText(password)) {
            return Optional.empty();
        }
        return empleadoRepository.findByCodigo(codigo)
                .filter(e -> password.equals(e.getContrasena()) && e.isActivo())
                .map(e -> (Usuario) e);
    }
}
