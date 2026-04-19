package com.hospy.controllers;

import com.hospy.models.MedicalRecord;
import com.hospy.models.Patient;
import com.hospy.repositories.MedicalRecordRepository;
import com.hospy.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientHistoryController {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    @GetMapping("/patient/history")
    public String viewHistory(Authentication authentication, Model model) {
        String email = authentication.getName();
        Patient patient = patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        List<MedicalRecord> records = medicalRecordRepository.findByPatientIdOrderByRecordDateDesc(patient.getId());
        model.addAttribute("records", records);
        return "patient-history";
    }
}
