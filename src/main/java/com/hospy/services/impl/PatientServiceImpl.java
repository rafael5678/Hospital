package com.hospy.services.impl;

import com.hospy.dto.PatientDto;
import com.hospy.dto.UserDto;
import com.hospy.models.Patient;
import com.hospy.models.User;
import com.hospy.repositories.PatientRepository;
import com.hospy.services.PatientService;
import com.hospy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Patient createPatient(PatientDto patientDto, String email, String plainPassword) {
        
        UserDto userDto = UserDto.builder().email(email).build();
        User newPatientUser = userService.registerPatient(userDto, plainPassword);

        Patient patient = Patient.builder()
                .user(newPatientUser)
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .dni(patientDto.getDni())
                .dateOfBirth(patientDto.getDateOfBirth())
                .city(patientDto.getCity())
                .bloodType(patientDto.getBloodType())
                .phoneNumber(patientDto.getPhoneNumber())
                .status(patientDto.getStatus())
                .build();
                
        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public Page<Patient> searchPatients(String name, int page, int size) {
        return patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                name, name, PageRequest.of(page, size)
        );
    }
}
