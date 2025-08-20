package org.example.arbook.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.arbook.model.entity.Role;
import org.example.arbook.model.entity.User;
import org.example.arbook.model.enums.Roles;
import org.example.arbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private Integer expirationTimeInMills;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return Jwts.builder()
                .subject(phoneNumber)
                .claim("id", user.getId())
                .claim("isActive", user.getIsActive())
                .claim("roles", user.getRoles().stream().map(role ->
                        role.getRoleName().name()).collect(Collectors.joining(","))
                )
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInMills))
                .signWith(getSecretKey())
                .compact();
    }


    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return true;
    }

    public User getUserObject(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        UUID id = UUID.fromString(claims.get("id", String.class));
        Boolean isActive = claims.get("isActive", Boolean.class);
        String strRoles = (String) claims.get("roles");

        List<Role> roles = Arrays.stream(strRoles.split(","))
                .map(roleStr -> new Role(Roles.valueOf(roleStr)))
                .toList();

        return User.builder()
                .id(id)
                .isActive(isActive)
                .phoneNumber(claims.getSubject())
                .roles(roles)
                .build();
    }
}
