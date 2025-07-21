package com.proyectoPWP.config;

import com.proyectoPWP.domain.usuario.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserConfig {
    
@ModelAttribute("usuario")
public Usuario getUsuarioEnSesion(HttpSession session) {
return (Usuario) session.getAttribute("usuarioLogueado");
}
}
