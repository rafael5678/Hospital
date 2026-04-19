package com.hospy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String contactNumber;
    private String availabilityHours;
    private String modality;
    private String virtualOfficeUrl;
}
