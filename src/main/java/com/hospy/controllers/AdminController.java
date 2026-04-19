package com.hospy.controllers;

import com.hospy.dto.DoctorDto;
import com.hospy.dto.PatientDto;
import com.hospy.services.DoctorService;
import com.hospy.services.PatientService;
import com.hospy.models.PatientStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/doctors")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping("/create")
    public String createDoctorForm() {
        return "create-doctor";
    }

    @PostMapping("/create")
    public String createDoctorProcess(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String specialty,
            @RequestParam String contactNumber,
            @RequestParam String availabilityHours,
            @RequestParam String modality,
            Model model
    ) {
        try {
            DoctorDto dto = DoctorDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .specialty(specialty)
                    .contactNumber(contactNumber)
                    .availabilityHours(availabilityHours)
                    .modality(modality)
                    .build();
            doctorService.createDoctor(dto, email, password);
            model.addAttribute("success", "Médico creado correctamente con credenciales vinculadas.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "create-doctor";
    }

    @GetMapping("/patients/create")
    public String createPatientForm() {
        return "create-patient";
    }

    @PostMapping("/patients/create")
    public String createPatientProcess(
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
                    .build();
            patientService.createPatient(dto, email, password);
            model.addAttribute("success", "Paciente " + firstName + " ingresado al sistema con identificador " + dni);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "create-patient";
    }
}
