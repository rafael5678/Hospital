package com.hospy.controllers;

import com.hospy.models.*;
import com.hospy.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor/consultation")
public class ConsultationController {

    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @GetMapping("/{appointmentId}")
    public String startConsultation(@PathVariable Long appointmentId, Model model, Authentication authentication) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
                
        if (!appointment.getDoctor().getUser().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("No autorizado para acceder a esta cita");
        }
        
        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            return "redirect:/doctor/dashboard?error=La cita debe estar confirmada para iniciar consulta";
        }
        
        model.addAttribute("appointment", appointment);
        return "consultation-room";
    }

    @PostMapping("/{appointmentId}")
    public String saveConsultation(
            @PathVariable Long appointmentId,
            @RequestParam Double bloodPressureSystolic,
            @RequestParam Double bloodPressureDiastolic,
            @RequestParam Double weight,
            @RequestParam Double temperature,
            @RequestParam String currentSymptoms,
            @RequestParam String physicalExamination,
            @RequestParam String diagnostics,
            Authentication authentication
    ) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // 1. Crear Consulta
        Consultation consultation = Consultation.builder()
                .appointment(appointment)
                .consultationDate(LocalDateTime.now())
                .bloodPressureSystolic(bloodPressureSystolic)
                .bloodPressureDiastolic(bloodPressureDiastolic)
                .weight(weight)
                .temperature(temperature)
                .currentSymptoms(currentSymptoms)
                .physicalExamination(physicalExamination)
                .diagnostics(diagnostics)
                .build();
        consultationRepository.save(consultation);

        // 2. Crear Registro en el Historial del Paciente
        MedicalRecord record = MedicalRecord.builder()
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .recordDate(LocalDateTime.now())
                .evolutionNotes("Síntomas: " + currentSymptoms + "\nExamen: " + physicalExamination)
                .diagnosis(diagnostics)
                .build();
        medicalRecordRepository.save(record);

        // 3. Cerrar Cita
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return "redirect:/doctor/dashboard?success=Consulta guardada y registro clínico actualizado satisfactoriamente";
    }
}
