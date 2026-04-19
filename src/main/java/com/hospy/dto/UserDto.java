package com.hospy.dto;

import com.hospy.models.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
    private Role role;
}
