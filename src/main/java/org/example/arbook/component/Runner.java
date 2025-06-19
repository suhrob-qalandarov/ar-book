package org.example.arbook.component;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.entity.Role;
import org.example.arbook.model.entity.User;
import org.example.arbook.model.enums.Roles;
import org.example.arbook.repository.RoleRepository;
import org.example.arbook.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args)  {
        if (roleRepository.findAll().isEmpty()) {
            List<Role> roles = roleRepository.saveAll(
                    List.of(
                            new Role(Roles.ROLE_ADMIN),
                            new Role(Roles.ROLE_USER)
                    ));
            if (userRepository.findAll().isEmpty()) {
                userRepository.save(
                        User.builder()
                                .firstName("Nick")
                                .lastName("Holden")
                                .phoneNumber("1")
                                .password(passwordEncoder.encode("1"))
                                .roles(roles)
                                .build()
                );

            }
        }

    }
}
