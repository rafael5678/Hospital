package com.hospy.services.impl;

import com.hospy.dto.UserDto;
import com.hospy.models.Role;
import com.hospy.models.User;
import com.hospy.repositories.UserRepository;
import com.hospy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerAdmin(UserDto userDto, String plainPassword) {
        return createUser(userDto.getEmail(), plainPassword, Role.ADMIN);
    }

    @Override
    public User registerDoctor(UserDto userDto, String plainPassword) {
        return createUser(userDto.getEmail(), plainPassword, Role.DOCTOR);
    }

    @Override
    public User registerPatient(UserDto userDto, String plainPassword) {
        return createUser(userDto.getEmail(), plainPassword, Role.PATIENT);
    }
    
    private User createUser(String email, String plainPassword, Role role) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(plainPassword))
                .role(role)
                .build();
        return userRepository.save(user);
    }
}
