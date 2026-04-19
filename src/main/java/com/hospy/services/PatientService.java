package com.hospy.services;

import com.hospy.dto.PatientDto;
import com.hospy.models.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {
    Patient createPatient(PatientDto patientDto, String email, String plainPassword);
    Patient getPatientById(Long id);
    Page<Patient> searchPatients(String name, int page, int size);
}
