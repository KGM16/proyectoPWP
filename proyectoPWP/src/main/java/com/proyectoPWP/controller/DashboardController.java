package com.proyectoPWP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/usuario")
    public String userDashboard() {
        return "redirect:/usuario/dashboard";
    }

    @GetMapping("/empleado")
    public String empleadoDashboard() {
        return "empleado/dashboard";
    }


}
