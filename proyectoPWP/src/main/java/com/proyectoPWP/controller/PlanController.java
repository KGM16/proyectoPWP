package com.proyectoPWP.controller;

import com.proyectoPWP.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    @GetMapping("/planes")
    public String listarPlanes(Model model) {
        model.addAttribute("planes", planRepository.findAll());
        return "planes"; 
    }
}
