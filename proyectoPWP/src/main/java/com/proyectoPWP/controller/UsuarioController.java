package com.proyectoPWP.controller;

import com.proyectoPWP.domain.clase.Clase;
import com.proyectoPWP.domain.clase.ClaseMatriculada;
import com.proyectoPWP.domain.usuario.Usuario;
import com.proyectoPWP.repository.ClaseMatriculadaRepository;
import com.proyectoPWP.repository.ClaseRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ClaseMatriculadaRepository claseMatriculadaRepository;

    @GetMapping("/usuario/dashboard")
    public String dashboardUsuario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login/usuario";
        }

        model.addAttribute("usuario", usuario);
        return "usuario/dashboard"; // <- Esto espera "templates/usuario/dashboard.html"
    }

    @GetMapping("/usuario/agendar")
    public String mostrarClasesDisponibles(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        List<Clase> clases = claseRepository.findAll();
        model.addAttribute("clases", clases);

        if (usuario != null) {
            List<ClaseMatriculada> clasesAgendadas = claseMatriculadaRepository.findByUsuario(usuario);
            model.addAttribute("clasesAgendadas", clasesAgendadas);
        }

        return "usuario/agendar";
    }

    @PostMapping("/usuario/agendar/{id}")
    public String matricularClase(
            @PathVariable("id") Long idClase,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login/usuario";
        }

        Optional<Clase> claseOpt = claseRepository.findById(idClase);

        if (claseOpt.isPresent()) {
            ClaseMatriculada nueva = new ClaseMatriculada();
            nueva.setUsuario(usuario);
            nueva.setClase(claseOpt.get());
            claseMatriculadaRepository.save(nueva);

            model.addAttribute("mensaje", "¡Clase matriculada con éxito!");
        }

// Obtener todas las clases nuevamente
        List<Clase> clases = claseRepository.findAll();
        model.addAttribute("clases", clases);

// Obtener clases agendadas del usuario
        List<ClaseMatriculada> clasesAgendadas = claseMatriculadaRepository.findByUsuario(usuario);
        model.addAttribute("clasesAgendadas", clasesAgendadas);

        return "usuario/agendar";
    }

}
