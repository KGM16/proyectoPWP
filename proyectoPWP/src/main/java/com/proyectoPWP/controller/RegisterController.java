package com.proyectoPWP.controller;

import com.proyectoPWP.domain.cliente.Cliente;
import com.proyectoPWP.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/register")
    public String form() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String process(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            RedirectAttributes ra) {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            ra.addFlashAttribute("error", "Campos requeridos");
            return "redirect:/register";
        }
        if (clienteRepository.existsByEmail(email)) {
            ra.addFlashAttribute("error", "El correo ya existe");
            return "redirect:/register";
        }
        Cliente c = new Cliente();
        c.setEmail(email);
        c.setContrasena(password);
        c.setNombre(firstName);
        c.setApellido(lastName);
        clienteRepository.save(c);
        ra.addFlashAttribute("success", "Registro exitoso, inicia sesión");
        return "redirect:/login/usuario";
    }
}
