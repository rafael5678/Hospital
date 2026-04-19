package com.hospy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "role", required = false, defaultValue = "patient") String role, 
                        @RequestParam(name = "error", required = false) String error,
                        Model model) {
        model.addAttribute("role", role);
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas. Por favor, intente de nuevo.");
        }
        return "login";
    }
}
