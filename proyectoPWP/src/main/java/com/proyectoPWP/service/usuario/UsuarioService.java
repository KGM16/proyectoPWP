package com.proyectoPWP.service.usuario;

import com.proyectoPWP.domain.usuario.Usuario;
import com.proyectoPWP.dto.RegistroUsuarioDTO;
import com.proyectoPWP.repository.usuario.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios(boolean activo) {
        var lista = usuarioRepository.findAll();
        
        // Filtrar por estado activo si es necesario
        if (activo) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }
    
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioRepository.findById(usuario.getIdUsuario())
                .orElse(null);
    }
    
    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
    
    @Transactional(readOnly = true)
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
    
    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public boolean delete(Usuario usuario) {
        try {
            usuarioRepository.delete(usuario);
            usuarioRepository.flush();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    @Transactional
    public Usuario registrarNuevoUsuario(RegistroUsuarioDTO registroDTO) {
        // Verificar si el correo ya está registrado
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        // Crear y configurar el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellidos(registroDTO.getApellidos());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setTelefono(registroDTO.getTelefono());
        usuario.setFechaNacimiento(registroDTO.getFechaNacimiento());
        usuario.setGenero(registroDTO.getGenero());
        usuario.setObjetivo(registroDTO.getObjetivo());
        
        // Codificar la contraseña
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        
        // Configurar valores por defecto
        usuario.setActivo(true);
        usuario.setRol("USUARIO");
        
        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
