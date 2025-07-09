package com.proyectoPWP.controller;

import com.proyectoPWP.domain.plan.Plan;
import com.proyectoPWP.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/planes-gestion")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    @GetMapping("")
    public String listarPlanes(Model model) {
        model.addAttribute("planes", planRepository.findAll());
        model.addAttribute("plan", new Plan()); // Para el formulario de agregar
        return "admin/administrarPlanes";
    }

    @PostMapping("/guardar")
    public String guardarPlan(@ModelAttribute("plan") Plan plan, RedirectAttributes redirectAttributes) {
        try {
            planRepository.save(plan);
            redirectAttributes.addFlashAttribute("success", "Plan guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el plan: " + e.getMessage());
        }
        return "redirect:/admin/planes-gestion";
    }

    @PostMapping("/editar")
    public String editarPlan(@ModelAttribute("plan") Plan plan, RedirectAttributes redirectAttributes) {
        try {
            if (plan.getId() != null && planRepository.existsById(plan.getId())) {
                planRepository.save(plan);
                redirectAttributes.addFlashAttribute("success", "Plan actualizado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "No se encontró el plan a editar");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el plan: " + e.getMessage());
        }
        return "redirect:/admin/planes-gestion";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPlan(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            planRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Plan eliminado exitosamente");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el plan porque está siendo utilizado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el plan: " + e.getMessage());
        }
        return "redirect:/admin/planes-gestion";
    }
}
