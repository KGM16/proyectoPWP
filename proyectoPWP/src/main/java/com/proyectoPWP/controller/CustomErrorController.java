package com.proyectoPWP.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == 404) {
                model.addAttribute("error", "Página no encontrada");
                model.addAttribute("message", "La página que buscas no existe.");
                return "error/404";
            } else if (statusCode == 500) {
                model.addAttribute("error", "Error interno del servidor");
                model.addAttribute("message", "Ha ocurrido un error inesperado.");
                return "error/500";
            } else if (statusCode == 403) {
                model.addAttribute("error", "Acceso denegado");
                model.addAttribute("message", "No tienes permisos para acceder a esta página.");
                return "error/403";
            }
        }
        
        model.addAttribute("error", "Error");
        model.addAttribute("message", "Ha ocurrido un error inesperado.");
        return "error/error";
    }
}
