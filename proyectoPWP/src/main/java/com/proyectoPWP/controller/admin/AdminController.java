package com.proyectoPWP.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("")
    public String panelAdmin(Model model) {
        model.addAttribute("tituloPagina", "Panel de Administración");
        
        // Add dashboard statistics (you'll need to implement these services)
        model.addAttribute("totalUsuarios", 0);
        model.addAttribute("totalEmpleados", 0);
        model.addAttribute("totalSedes", 0);
        model.addAttribute("ingresosMensuales", 0);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/usuarios")
    public String administrarUsuarios(Model model) {
        model.addAttribute("tituloPagina", "Administrar Usuarios");
        return "admin/administrarUsuarios";
    }
    
    @GetMapping("/empleados")
    public String administrarEmpleados(Model model) {
        model.addAttribute("tituloPagina", "Administrar Empleados");
        return "admin/administrarEmpleados";
    }
    
    @GetMapping("/planes")
    public String administrarPlanes(Model model) {
        model.addAttribute("tituloPagina", "Administrar Planes");
        return "admin/administrarPlanes";
    }
    
    @GetMapping("/sedes")
    public String administrarSedes(Model model) {
        model.addAttribute("tituloPagina", "Administrar Sedes");
        return "admin/administrarSedes";
    }
    
    @GetMapping("/rutinas")
    public String administrarRutinas(Model model) {
        model.addAttribute("tituloPagina", "Administrar Rutinas");
        return "admin/administarRutinas";
    }
    
    @GetMapping("/ejercicios")
    public String administrarEjercicios(Model model) {
        model.addAttribute("tituloPagina", "Administrar Ejercicios");
        return "admin/administrarEjercicios";
    }
    
    @GetMapping("/ofertas")
    public String administrarOfertas(Model model) {
        model.addAttribute("tituloPagina", "Administrar Ofertas");
        return "admin/administrarOfertas";
    }
}
