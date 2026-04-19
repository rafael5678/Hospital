package com.hospy.dto;

import com.hospy.models.PatientStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private LocalDate dateOfBirth;
    private String city;
    private String bloodType;
    private String phoneNumber;
    private PatientStatus status;
}
