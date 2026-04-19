package com.hospy.services;

import com.hospy.models.User;
import com.hospy.dto.UserDto;

public interface UserService {
    User registerAdmin(UserDto userDto, String plainPassword);
    User registerDoctor(UserDto userDto, String plainPassword);
    User registerPatient(UserDto userDto, String plainPassword);
}
