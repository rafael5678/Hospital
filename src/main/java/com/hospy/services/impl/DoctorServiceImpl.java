package com.hospy.services.impl;

import com.hospy.dto.DoctorDto;
import com.hospy.dto.UserDto;
import com.hospy.models.Doctor;
import com.hospy.models.User;
import com.hospy.repositories.DoctorRepository;
import com.hospy.services.DoctorService;
import com.hospy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Doctor createDoctor(DoctorDto doctorDto, String email, String plainPassword) {
        User newDoctorUser = userService.registerDoctor(
                UserDto.builder().email(email).build(), 
                plainPassword
        );

        Doctor doctor = Doctor.builder()
                .user(newDoctorUser)
                .firstName(doctorDto.getFirstName())
                .lastName(doctorDto.getLastName())
                .specialty(doctorDto.getSpecialty())
                .contactNumber(doctorDto.getContactNumber())
                .availabilityHours(doctorDto.getAvailabilityHours())
                .modality(doctorDto.getModality())
                .build();
                
        return doctorRepository.save(doctor);
    }
}
