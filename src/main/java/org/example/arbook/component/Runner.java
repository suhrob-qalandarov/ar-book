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
        if (roleRepository.count() == 0) {
            List<Role> roles = roleRepository.saveAll(
                    List.of(
                            new Role(Roles.ROLE_USER),
                            new Role(Roles.ROLE_ADMIN)
                    ));
            if (userRepository.count() == 0) {
                userRepository.save(
                        User.builder()
                                .firstName("Nick")
                                .lastName("Holden")
                                .phoneNumber("998901234567")
                                .email("nick@gmail.com")
                                .password(passwordEncoder.encode("1234567Sk"))
                                .isActive(true)
                                .roles(roles)
                                .build()
                );
            }
        }
    }
}
