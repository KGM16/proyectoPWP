package com.proyectoPWP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Retorna la página principal
    }
    
    @GetMapping("/clase-gratis")
    public String claseGratis(RedirectAttributes ra) {
        ra.addFlashAttribute("message", "¡Regístrate para tomar tu clase gratis!");
        ra.addFlashAttribute("messageType", "info");
        return "redirect:/register"; // Redirige al registro
    }
    
    @GetMapping("/tomar-clase-gratis")
    public String tomarClaseGratis(RedirectAttributes ra) {
        ra.addFlashAttribute("message", "¡Regístrate para acceder a tu clase de prueba gratuita!");
        ra.addFlashAttribute("messageType", "success");
        return "redirect:/register";
    }
    
    @GetMapping("/planes")
    public String planes(Model model) {
        return "planes"; // Página de planes
    }
    
    @GetMapping("/entrenador")
    public String entrenador() {
        return "entrenador/perfilEntrenador"; // Dashboard entrenador
    }
    
    @GetMapping("/empleado")
    public String empleado() {
        return "empleado/dashboard"; // Dashboard empleado
    }
    
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto"; // Página de contacto
    }
    
    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros"; // Página sobre nosotros
    }
    
    @GetMapping("/servicios")
    public String servicios() {
        return "servicios"; // Página de servicios
    }
}
