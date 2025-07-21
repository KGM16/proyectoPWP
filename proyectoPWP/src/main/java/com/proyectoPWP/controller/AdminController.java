package com.proyectoPWP.controller;

import com.proyectoPWP.domain.admin.Administrador;
import com.proyectoPWP.domain.usuario.Usuario;
import com.proyectoPWP.domain.cliente.Cliente;
import com.proyectoPWP.repository.PlanRepository;
import com.proyectoPWP.repository.UsuarioRepository;
import com.proyectoPWP.repository.ClaseRepository;
import com.proyectoPWP.repository.EjercicioRepository;
import com.proyectoPWP.repository.RutinaRepository;
import com.proyectoPWP.repository.SedeRepository;
import com.proyectoPWP.repository.OfertaRepository;
import com.proyectoPWP.repository.EmpleadoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private RutinaRepository rutinaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @GetMapping("/admin")
    public String panel(HttpSession session, Model model, RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !(usuario instanceof Administrador)) {
            ra.addFlashAttribute("error", "Acceso denegado");
            return "redirect:/login";
        }
        
        // Obtener estadísticas para el dashboard
        long totalUsuarios = usuarioRepository.count();
        long totalClases = claseRepository.count();
        long totalPlanes = planRepository.count();
        long totalSedes = sedeRepository.count();
        long totalEjercicios = ejercicioRepository.count();
        long totalRutinas = rutinaRepository.count();
        long totalEmpleados = empleadoRepository.count();
        long totalOfertas = ofertaRepository.count();
        
        // Estadísticas más específicas
        long usuariosActivos = usuarioRepository.countByActivoTrue();
        long clasesHoy = claseRepository.countClasesHoy();
        long nuevosUsuariosEsteMes = usuarioRepository.countNuevosUsuariosEsteMes();
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalClases", totalClases);
        model.addAttribute("totalPlanes", totalPlanes);
        model.addAttribute("totalSedes", totalSedes);
        model.addAttribute("totalEjercicios", totalEjercicios);
        model.addAttribute("totalRutinas", totalRutinas);
        model.addAttribute("totalEmpleados", totalEmpleados);
        model.addAttribute("totalOfertas", totalOfertas);
        model.addAttribute("usuariosActivos", usuariosActivos);
        model.addAttribute("clasesHoy", clasesHoy);
        model.addAttribute("nuevosUsuariosEsteMes", nuevosUsuariosEsteMes);
        model.addAttribute("activePage", "dashboard");
        
        return "admin/adminPanel"; // templates/admin/adminPanel.html
    }

    @GetMapping("/admin/planes")
    public String planes(Model model) {
        model.addAttribute("planes", planRepository.findAll());
        model.addAttribute("nuevoPlan", new com.proyectoPWP.domain.plan.Plan());
        return "admin/administrarPlanes";
    }

    @PostMapping("/admin/planes/save")
    public String savePlan(@ModelAttribute("nuevoPlan") com.proyectoPWP.domain.plan.Plan plan,
                           RedirectAttributes ra){
        planRepository.save(plan);
        ra.addFlashAttribute("success","Plan guardado correctamente");
        return "redirect:/admin/planes";
    }

    @PostMapping("/admin/planes/delete")
    public String deletePlan(@RequestParam Long id, RedirectAttributes ra){
        planRepository.deleteById(id);
        ra.addFlashAttribute("success","Plan eliminado");
        return "redirect:/admin/planes";
    }

    @GetMapping("/admin/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/administrarUsuarios";
    }

    @PostMapping("/admin/usuarios/save")
    public String saveUsuario(@ModelAttribute Cliente cliente,
                              RedirectAttributes ra){
        try {
            usuarioRepository.save(cliente);
            ra.addFlashAttribute("success","Usuario guardado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error","Error al guardar usuario: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/admin/usuarios/delete")
    public String deleteUsuario(@RequestParam Long id, RedirectAttributes ra){
        try {
            // Verificar si el usuario tiene relaciones antes de eliminar
            Usuario usuario = usuarioRepository.findById(id).orElse(null);
            if (usuario == null) {
                ra.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/admin/usuarios";
            }
            
            // Intentar eliminar
            usuarioRepository.deleteById(id);
            ra.addFlashAttribute("success", "Usuario eliminado correctamente");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "No se puede eliminar el usuario porque tiene clases o rutinas asignadas. Primero debe reasignar o eliminar estas relaciones.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/admin/clases/save")
    public String saveClase(@ModelAttribute com.proyectoPWP.domain.clase.Clase clase, RedirectAttributes ra){
        claseRepository.save(clase);
        ra.addFlashAttribute("success","Clase guardada");
        return "redirect:/admin/clases";
    }

    @PostMapping("/admin/clases/delete")
    public String deleteClase(@RequestParam Long id, RedirectAttributes ra){
        claseRepository.deleteById(id);
        ra.addFlashAttribute("success","Clase eliminada");
        return "redirect:/admin/clases";
    }

    @GetMapping("/admin/clases")
    public String clases(Model model) {
        model.addAttribute("clases", claseRepository.findAll());
        return "admin/administrarClases";
    }

    @PostMapping("/admin/ejercicios/save")
    public String saveEjercicio(@ModelAttribute com.proyectoPWP.domain.ejercicio.Ejercicio ejercicio, RedirectAttributes ra){
        ejercicioRepository.save(ejercicio);
        ra.addFlashAttribute("success","Ejercicio guardado");
        return "redirect:/admin/ejercicios";
    }

    @PostMapping("/admin/ejercicios/delete")
    public String deleteEjercicio(@RequestParam Long id, RedirectAttributes ra){
        ejercicioRepository.deleteById(id);
        ra.addFlashAttribute("success","Ejercicio eliminado");
        return "redirect:/admin/ejercicios";
    }

    @GetMapping("/admin/ejercicios")
    public String ejercicios(Model model) {
        model.addAttribute("ejercicios", ejercicioRepository.findAll());
        return "admin/administrarEjercicios";
    }

    @PostMapping("/admin/rutinas/save")
    public String saveRutina(@ModelAttribute com.proyectoPWP.domain.rutina.Rutina rutina, RedirectAttributes ra){
        rutinaRepository.save(rutina);
        ra.addFlashAttribute("success","Rutina guardada");
        return "redirect:/admin/rutinas";
    }

    @PostMapping("/admin/rutinas/delete")
    public String deleteRutina(@RequestParam Long id, RedirectAttributes ra){
        rutinaRepository.deleteById(id);
        ra.addFlashAttribute("success","Rutina eliminada");
        return "redirect:/admin/rutinas";
    }

    @GetMapping("/admin/rutinas")
    public String rutinas(Model model) {
        model.addAttribute("rutinas", rutinaRepository.findAll());
        return "admin/administrarRutinas";
    }

    @PostMapping("/admin/empleados/save")
    public String saveEmpleado(@ModelAttribute com.proyectoPWP.domain.empleado.Empleado empleado, RedirectAttributes ra){
        empleadoRepository.save(empleado);
        ra.addFlashAttribute("success","Empleado guardado");
        return "redirect:/admin/empleados";
    }

    @PostMapping("/admin/empleados/delete")
    public String deleteEmpleado(@RequestParam Long id, RedirectAttributes ra){
        empleadoRepository.deleteById(id);
        ra.addFlashAttribute("success","Empleado eliminado");
        return "redirect:/admin/empleados";
    }

    @GetMapping("/admin/empleados")
    public String empleados(Model model) {
        model.addAttribute("empleados", empleadoRepository.findAll());
        return "admin/administrarEmpleados";
    }

    @PostMapping("/admin/sedes/save")
    public String saveSede(@ModelAttribute com.proyectoPWP.domain.sede.Sede sede, RedirectAttributes ra){
        sedeRepository.save(sede);
        ra.addFlashAttribute("success","Sede guardada");
        return "redirect:/admin/sedes";
    }

    @PostMapping("/admin/sedes/delete")
    public String deleteSede(@RequestParam Long id, RedirectAttributes ra){
        sedeRepository.deleteById(id);
        ra.addFlashAttribute("success","Sede eliminada");
        return "redirect:/admin/sedes";
    }

    @GetMapping("/admin/sedes")
    public String sedes(Model model) {
        model.addAttribute("sedes", sedeRepository.findAll());
        return "admin/administarSedes";
    }

    @GetMapping("/admin/ofertas")
    public String ofertas(Model model) {
        model.addAttribute("ofertas", ofertaRepository.findAll());
        model.addAttribute("oferta", new com.proyectoPWP.domain.oferta.Oferta());
        return "admin/administrarOfertas";
    }
    
    @PostMapping("/admin/ofertas/guardar")
    public String guardarOferta(@ModelAttribute("oferta") com.proyectoPWP.domain.oferta.Oferta oferta, 
                              RedirectAttributes ra) {
        ofertaRepository.save(oferta);
        ra.addFlashAttribute("success", "Oferta guardada correctamente");
        return "redirect:/admin/ofertas";
    }
    
    @PostMapping("/admin/ofertas/editar")
    public String editarOferta(@ModelAttribute("oferta") com.proyectoPWP.domain.oferta.Oferta oferta, 
                             @RequestParam("id") Long id,
                             RedirectAttributes ra) {
        oferta.setId(id);
        ofertaRepository.save(oferta);
        ra.addFlashAttribute("success", "Oferta actualizada correctamente");
        return "redirect:/admin/ofertas";
    }
    
    @PostMapping("/admin/ofertas/delete/{id}")
    public String eliminarOferta(@PathVariable("id") Long id, RedirectAttributes ra) {
        ofertaRepository.deleteById(id);
        ra.addFlashAttribute("success", "Oferta eliminada correctamente");
        return "redirect:/admin/ofertas";
    }
}
