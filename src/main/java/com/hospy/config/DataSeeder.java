package com.hospy.config;

import com.hospy.dto.UserDto;
import com.hospy.models.Role;
import com.hospy.repositories.UserRepository;
import com.hospy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si existe el admin, si no, crearlo.
        if (userRepository.findAll().stream().noneMatch(u -> u.getRole() == Role.ADMIN)) {
            UserDto adminDto = UserDto.builder()
                    .email("admin@hospy.com")
                    .role(Role.ADMIN)
                    .build();
            userService.registerAdmin(adminDto, "admin123");
            System.out.println("========== ADMIN CREADO ==========");
            System.out.println("Email: admin@hospy.com");
            System.out.println("Pass: admin123");
            System.out.println("==================================");
        }
    }
}
