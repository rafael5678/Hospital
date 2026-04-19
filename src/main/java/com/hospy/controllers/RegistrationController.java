package com.hospy.controllers;

import com.hospy.dto.PatientDto;
import com.hospy.models.PatientStatus;
import com.hospy.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final PatientService patientService;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dni,
            Model model
    ) {
        try {
            PatientDto dto = PatientDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .dni(dni)
                    .status(PatientStatus.ACTIVE)
                    .dateOfBirth(LocalDate.now()) // Default for simplicity in UI
                    .build();
            
            patientService.createPatient(dto, email, password);
            return "redirect:/login?role=patient&registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
