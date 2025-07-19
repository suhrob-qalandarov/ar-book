package org.example.arbook.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.model.entity.Language;
import org.example.arbook.model.entity.Role;
import org.example.arbook.model.entity.User;
import org.example.arbook.model.enums.Roles;
import org.example.arbook.repository.LanguageRepository;
import org.example.arbook.repository.RoleRepository;
import org.example.arbook.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LanguageRepository languageRepository;

    @Transactional
    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            List<Role> roles = roleRepository.saveAll(
                    List.of(
                            new Role(Roles.ROLE_USER),
                            new Role(Roles.ROLE_ADMIN)
                    ));
            if (userRepository.count() == 0) {
                User user = User.builder()
                        .firstName("Nick")
                        .lastName("Holden")
                        .phoneNumber("+998901234567")
                        .email("nick@gmail.com")
                        .password(passwordEncoder.encode("1"))
                        .isActive(true)
                        .roles(roles)
                        .build();
                User savedUser = userRepository.save(user);
                savedUser.setPassword(passwordEncoder.encode(user.getId().toString()));
            }
        }

        if (languageRepository.count() == 0) {
            languageRepository.saveAll(List.of(
                    Language.builder().name("O'zbek").build(),
                    Language.builder().name("English").build(),
                    Language.builder().name("Russian").build(),
                    Language.builder().name("Mandarin(Chinese)").build(),
                    Language.builder().name("Hindi").build(),
                    Language.builder().name("Spanish").build(),
                    Language.builder().name("Arabic").build(),
                    Language.builder().name("French").build(),
                    Language.builder().name("Bengali").build(),
                    Language.builder().name("Portuguese").build(),
                    Language.builder().name("Indonesian").build(),
                    Language.builder().name("German").build(),
                    Language.builder().name("Japanese").build(),
                    Language.builder().name("Turkish").build(),
                    Language.builder().name("Vietnamese").build(),
                    Language.builder().name("Korean").build(),
                    Language.builder().name("Persian").build(),
                    Language.builder().name("Thai").build(),
                    Language.builder().name("Italian").build(),
                    Language.builder().name("Swahili").build()
            ));


            log.error("Added 20 the most popular and widely spoken languages starting with O'zbek, Русский, English");
        }
    }
}
