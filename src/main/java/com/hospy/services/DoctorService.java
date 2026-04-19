package com.hospy.services;

import com.hospy.dto.DoctorDto;
import com.hospy.models.Doctor;

public interface DoctorService {
    Doctor createDoctor(DoctorDto doctorDto, String email, String password);
}
