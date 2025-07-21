package com.proyectoPWP.controller;

import com.proyectoPWP.domain.admin.Administrador;
import com.proyectoPWP.domain.cliente.Cliente;
import com.proyectoPWP.domain.empleado.Empleado;
import com.proyectoPWP.domain.entrenador.Entrenador;
import com.proyectoPWP.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private com.proyectoPWP.service.AutenticacionService authService;

    @GetMapping("/login")
    public String chooseLogin(HttpSession session, Model model) {
        // Si ya está autenticado, redirigir según el tipo de usuario
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return redirectByUserType(usuario);
        }
        return "auth/tipoLogin"; 
    }

    @GetMapping("/login/empleado")
    public String loginEmpleado(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return redirectByUserType(usuario);
        }
        return "auth/loginEmpleado";
    }

    @GetMapping("/login/usuario")
    public String loginUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return redirectByUserType(usuario);
        }
        return "auth/loginUsuario";
    }
    

    
    @PostMapping("/login/empleado")
    public String processEmpleado(@RequestParam("codigo") String codigo,
                                  @RequestParam("password") String password,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        try {
            // Validaciones básicas
            if (codigo == null || codigo.trim().isEmpty()) {
                ra.addFlashAttribute("error", "El código de empleado es requerido");
                return "redirect:/login/empleado";
            }
            
            if (password == null || password.trim().isEmpty()) {
                ra.addFlashAttribute("error", "La contraseña es requerida");
                return "redirect:/login/empleado";
            }

            return authService.autenticarEmpleado(codigo.trim(), password)
                    .map(u -> {
                        // Guardar usuario en sesión
                        session.setAttribute("usuario", u);
                        session.setAttribute("tipoUsuario", u.getClass().getSimpleName());
                        
                        if (u instanceof Administrador) {
                            ra.addFlashAttribute("success", "Bienvenido Administrador " + u.getNombre());
                            return "redirect:/admin";
                        } else if (u instanceof Entrenador) {
                            ra.addFlashAttribute("success", "Bienvenido Entrenador " + u.getNombre());
                            return "redirect:/entrenador";
                        } else {
                            ra.addFlashAttribute("success", "Bienvenido " + u.getNombre());
                            return "redirect:/empleado";
                        }
                    })
                    .orElseGet(() -> {
                        ra.addFlashAttribute("error", "Código o contraseña incorrectos");
                        return "redirect:/login/empleado";
                    });
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error en el sistema. Intente nuevamente.");
            return "redirect:/login/empleado";
        }
    }

    @PostMapping("/login/usuario")
    public String processUsuario(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        try {
            // Validaciones básicas
            if (email == null || email.trim().isEmpty()) {
                ra.addFlashAttribute("error", "El email es requerido");
                return "redirect:/login/usuario";
            }
            
            if (password == null || password.trim().isEmpty()) {
                ra.addFlashAttribute("error", "La contraseña es requerida");
                return "redirect:/login/usuario";
            }
            
            return authService.autenticarUsuario(email.trim(), password)
                    .map(u -> {
                        // Guardar usuario en sesión
                        session.setAttribute("usuario", u);
                        session.setAttribute("tipoUsuario", "Cliente");
                        ra.addFlashAttribute("success", "Bienvenido " + u.getNombre());
                        return "redirect:/usuario";
                    })
                    .orElseGet(() -> {
                        ra.addFlashAttribute("error", "Email o contraseña incorrectos");
                        return "redirect:/login/usuario";
                    });
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error en el sistema. Intente nuevamente.");
            return "redirect:/login/usuario";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("mensaje", "Sesión cerrada exitosamente");
        return "redirect:/";
    }
    
    private String redirectByUserType(Usuario usuario) {
        if (usuario instanceof Administrador) {
            return "redirect:/admin";
        } else if (usuario instanceof Entrenador) {
            return "redirect:/entrenador";
        } else if (usuario instanceof Empleado) {
            return "redirect:/empleado";
        } else if (usuario instanceof Cliente) {
            return "redirect:/usuario";
        }
        return "redirect:/";
    }
}
