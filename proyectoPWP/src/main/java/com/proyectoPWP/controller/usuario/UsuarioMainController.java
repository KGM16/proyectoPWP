package com.proyectoPWP.controller.usuario;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
@PreAuthorize("hasRole('USUARIO')")
public class UsuarioMainController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tituloPagina", "Dashboard");
        return "usuario/dashboard";
    }
    
    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("tituloPagina", "Inicio");
        return "usuario/mainPage";
    }
    
    @GetMapping("/perfil")
    public String perfil(Model model) {
        model.addAttribute("tituloPagina", "Mi Perfil");
        return "usuario/perfil";
    }
    
    @GetMapping("/clases")
    public String misClases(Model model) {
        model.addAttribute("tituloPagina", "Mis Clases");
        return "usuario/clases";
    }
    
    @GetMapping("/rutina")
    public String miRutina(Model model) {
        model.addAttribute("tituloPagina", "Mi Rutina");
        return "usuario/rutina";
    }
    
    @GetMapping("/progreso")
    public String miProgreso(Model model) {
        model.addAttribute("tituloPagina", "Mi Progreso");
        return "usuario/progreso";
    }
    
    @GetMapping("/membresia")
    public String miMembresia(Model model) {
        model.addAttribute("tituloPagina", "Mi Membresía");
        return "usuario/membresia";
    }
}
