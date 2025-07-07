package com.proyectoPWP.controller.entrenador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entrenador")
@PreAuthorize("hasAnyRole('ENTRENADOR', 'ADMIN')")
public class EntrenadorController {

    @GetMapping("")
    public String panelEntrenador(Model model) {
        model.addAttribute("tituloPagina", "Panel del Entrenador");
        return "entrenador/perfilEntrenador";
    }
    
    @GetMapping("/horario")
    public String verHorario(Model model) {
        model.addAttribute("tituloPagina", "Mi Horario");
        return "entrenador/horario";
    }
    
    @GetMapping("/clientes")
    public String verClientes(Model model) {
        model.addAttribute("tituloPagina", "Mis Clientes");
        return "entrenador/clientes";
    }
    
    @GetMapping("/rutinas")
    public String gestionarRutinas(Model model) {
        model.addAttribute("tituloPagina", "Gestión de Rutinas");
        return "entrenador/rutinas";
    }
}
