package com.proyectoPWP.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private com.proyectoPWP.service.AutenticacionService authService;

    @GetMapping("/login")
    public String chooseLogin() {
        return "auth/tipoLogin";
    }

    @GetMapping("/login/empleado")
    public String loginEmpleado() {
        return "auth/loginEmpleado";
    }

    @GetMapping("/login/usuario")
    public String loginUsuario() {
        return "auth/loginUsuario";
    }

    @GetMapping("/login/register")
    public String registroUsuario() {
        return "auth/register";
    }

    @PostMapping("/login/empleado")
    public String processEmpleado(@RequestParam("codigo") String codigo,
            @RequestParam("password") String password,
            RedirectAttributes ra) {

        return authService.autenticarEmpleado(codigo, password)
                .map(u -> {
                    if (u instanceof com.proyectoPWP.domain.admin.Administrador) {
                        return "redirect:/admin";
                    } else {
                        return "redirect:/empleado";
                    }
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Credenciales inválidas");
                    return "redirect:/login/empleado";
                });
    }

    @PostMapping("/login/usuario")
    public String processUsuario(@RequestParam("email") String email,
            @RequestParam("password") String password,
            RedirectAttributes ra,
            HttpSession session) {
        return authService.autenticarUsuario(email, password)
                .map(usuario -> {
                    session.setAttribute("usuarioLogueado", usuario); // 
                    return "redirect:/usuario/dashboard";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Correo o contraseña incorrectos");
                    return "redirect:/login/usuario";
                });
    }
    
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login/usuario";
    }
}
