package com.hospy.controllers;

import com.hospy.models.Appointment;
import com.hospy.models.AppointmentStatus;
import com.hospy.models.Doctor;
import com.hospy.models.Patient;
import com.hospy.repositories.AppointmentRepository;
import com.hospy.repositories.DoctorRepository;
import com.hospy.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    // ----- PATIENT ENDPOINTS -----

    @GetMapping("/patient/appointments/new")
    public String newAppointmentForm(Authentication authentication, Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        // Generar lista de horas para simplificar (8 am to 5 pm)
        model.addAttribute("doctors", doctors);
        return "schedule-appointment";
    }

    @PostMapping("/patient/appointments/new")
    public String createAppointment(
            Authentication authentication,
            @RequestParam Long doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            @RequestParam String reason
    ) {
        String email = authentication.getName();
        Patient patient = patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
                
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(LocalDate.parse(appointmentDate))
                .appointmentTime(LocalTime.parse(appointmentTime))
                .reason(reason)
                .status(AppointmentStatus.PENDING)
                .build();
                
        appointmentRepository.save(appointment);
        return "redirect:/patient/dashboard?success=Cita Agendada y Pendiente de Confirmación";
    }

    // ----- DOCTOR ENDPOINTS -----

    @PostMapping("/doctor/appointments/{id}/status")
    public String updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String action,
            Authentication authentication
    ) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
                
        String email = authentication.getName();
        if (!appointment.getDoctor().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access Denied");
        }
        
        switch (action) {
            case "ACCEPT":
                appointment.setStatus(AppointmentStatus.CONFIRMED);
                break;
            case "REJECT":
                appointment.setStatus(AppointmentStatus.CANCELLED);
                break;
        }
        appointmentRepository.save(appointment);
        return "redirect:/doctor/dashboard";
    }
}
