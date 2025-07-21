package com.proyectoPWP.controller;

import com.proyectoPWP.domain.clase.Clase;
import com.proyectoPWP.domain.cliente.Cliente;
import com.proyectoPWP.domain.plan.Plan;
import com.proyectoPWP.domain.rutina.Rutina;
import com.proyectoPWP.domain.usuario.Usuario;
import com.proyectoPWP.repository.ClaseRepository;
import com.proyectoPWP.repository.PlanRepository;
import com.proyectoPWP.repository.RutinaRepository;
import com.proyectoPWP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private ClaseRepository claseRepository;
    
    @Autowired
    private RutinaRepository rutinaRepository;
    
    @Autowired
    private PlanRepository planRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    public String dashboard(HttpSession session, Model model, RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !(usuario instanceof Cliente)) {
            ra.addFlashAttribute("error", "Acceso denegado");
            return "redirect:/login";
        }

        Cliente cliente = (Cliente) usuario;
        
        // Obtener datos para el dashboard
        List<Clase> clasesProximas = claseRepository.findClasesProximas();
        List<Rutina> rutinasActivas = rutinaRepository.findByClienteAndEstado(cliente, "ACTIVA");
        List<Plan> planesDisponibles = planRepository.findAll();
        
        model.addAttribute("usuario", cliente);
        model.addAttribute("clasesProximas", clasesProximas);
        model.addAttribute("rutinasActivas", rutinasActivas);
        model.addAttribute("planesDisponibles", planesDisponibles);
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("showSidebar", true);
        
        return "usuario/dashboard";
    }

    @GetMapping("/clases")
    public String misClases(HttpSession session, Model model, RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !(usuario instanceof Cliente)) {
            ra.addFlashAttribute("error", "Acceso denegado");
            return "redirect:/login";
        }

        Cliente cliente = (Cliente) usuario;
        
        // Obtener clases disponibles y clases del usuario
        List<Clase> clasesDisponibles = claseRepository.findClasesConCupoDisponible();
        
        model.addAttribute("usuario", cliente);
        model.addAttribute("clasesDisponibles", clasesDisponibles);
        model.addAttribute("activePage", "clases");
        model.addAttribute("showSidebar", true);
        
        return "usuario/clases";
    }

    @GetMapping("/rutinas")
    public String misRutinas(HttpSession session, Model model, RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !(usuario instanceof Cliente)) {
            ra.addFlashAttribute("error", "Acceso denegado");
            return "redirect:/login";
        }

        Cliente cliente = (Cliente) usuario;
        
        // Obtener rutinas del cliente
        List<Rutina> rutinasPersonalizadas = rutinaRepository.findByClienteAndActivaTrue(cliente);
        List<Rutina> rutinasGenerales = rutinaRepository.findByEsPersonalizadaFalseAndActivaTrue();
        
        model.addAttribute("usuario", cliente);
        model.addAttribute("rutinasPersonalizadas", rutinasPersonalizadas);
        model.addAttribute("rutinasGenerales", rutinasGenerales);
        model.addAttribute("activePage", "rutinas");
        model.addAttribute("showSidebar", true);
        
        return "usuario/rutinas";
    }

    @GetMapping("/plan-alimentacion")
    public String planAlimentacion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Aquí se cargarían los datos del plan de alimentación del usuario
        model.addAttribute("usuario", usuario);
        return "usuario/plan-alimentacion";
    }
    
    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuario", usuario);
        
        return "usuario/perfil";
    }
    
    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam Map<String, String> params, 
                                   HttpSession session, 
                                   RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        try {
            // Actualizar datos del usuario
            usuario.setNombre(params.get("nombre"));
            usuario.setApellido(params.get("apellido"));
            usuario.setTelefono(params.get("telefono"));
            usuario.setDireccion(params.get("direccion"));
            
            if (params.get("peso") != null && !params.get("peso").isEmpty()) {
                usuario.setPeso(Double.parseDouble(params.get("peso")));
            }
            if (params.get("altura") != null && !params.get("altura").isEmpty()) {
                usuario.setAltura(Double.parseDouble(params.get("altura")));
            }
            
            // Guardar en base de datos
            usuarioRepository.save(usuario);
            
            // Actualizar sesión
            session.setAttribute("usuario", usuario);
            
            ra.addFlashAttribute("success", "Perfil actualizado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al actualizar el perfil");
        }
        
        return "redirect:/usuario/perfil";
    }
    
    @PostMapping("/perfil/cambiar-password")
    public String cambiarPassword(@RequestParam("passwordActual") String passwordActual,
                                  @RequestParam("passwordNueva") String passwordNueva,
                                  @RequestParam("passwordConfirmar") String passwordConfirmar,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Validar contraseña actual
        if (!passwordActual.equals(usuario.getContrasena())) {
            ra.addFlashAttribute("error", "La contraseña actual es incorrecta");
            return "redirect:/usuario/perfil";
        }
        
        // Validar que las nuevas contraseñas coincidan
        if (!passwordNueva.equals(passwordConfirmar)) {
            ra.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/usuario/perfil";
        }
        
        // Validar longitud mínima
        if (passwordNueva.length() < 6) {
            ra.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            return "redirect:/usuario/perfil";
        }
        
        try {
            // Actualizar contraseña
            usuario.setContrasena(passwordNueva);
            usuarioRepository.save(usuario);
            
            // Actualizar sesión
            session.setAttribute("usuario", usuario);
            
            ra.addFlashAttribute("success", "Contraseña cambiada correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al cambiar la contraseña");
        }
        
        return "redirect:/usuario/perfil";
    }
}
