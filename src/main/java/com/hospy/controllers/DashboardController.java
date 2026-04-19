package com.hospy.controllers;

import com.hospy.models.Doctor;
import com.hospy.models.Patient;
import com.hospy.repositories.AppointmentRepository;
import com.hospy.repositories.DoctorRepository;
import com.hospy.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/dashboard")
    public String mainDashboard(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));
        
        if (isAdmin) {
            return "redirect:/admin/dashboard";
        } else if (isDoctor) {
            return "redirect:/doctor/dashboard";
        } else {
            return "redirect:/patient/dashboard";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        long totalDoctors = doctorRepository.count();
        long totalPatients = patientRepository.count();
        long totalAppointments = appointmentRepository.count();
        
        model.addAttribute("totalDoctors", totalDoctors);
        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("totalAppointments", totalAppointments);
        model.addAttribute("doctors", doctorRepository.findAll());
        
        return "dashboard-admin";
    }

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        Doctor doctor = doctorRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Medical profile not found"));
        
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointmentRepository.findByDoctorIdOrderByAppointmentDateDesc(doctor.getId()));
        
        return "dashboard-doctor";
    }

    @GetMapping("/patient/dashboard")
    public String patientDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        Patient patient = patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        
        model.addAttribute("patient", patient);
        model.addAttribute("appointments", appointmentRepository.findByPatientIdOrderByAppointmentDateDesc(patient.getId()));
        
        return "dashboard-patient";
    }
}
