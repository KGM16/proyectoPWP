package com.proyectoPWP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/usuario")
    public String userDashboard() {
        return "usuario";
    }

    @GetMapping("/empleado")
    public String empleadoDashboard() {
        return "empleado/dashboard";
    }


}
