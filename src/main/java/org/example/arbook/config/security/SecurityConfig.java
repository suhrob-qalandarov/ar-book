package org.example.arbook.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("http://localhost:3000/",
                                        "http://localhost:3000/**")
                                .permitAll()
                                .requestMatchers(
                                        "/admin/main-page.html",
                                        "/admin/book-page.html",
                                        "/admin/book-page.html/**",
                                        "/admin/orders.html/**"

                                ).permitAll()
                                .requestMatchers(
                                        "/"
                                        , "/index.html/**"
                                        , "/auth/home2.html"
                                        , "/auth/register.html"
                                        , "/auth/login.html"
                                        , "/auth/verifyPhone.html"
                                ).permitAll()
//                                .requestMatchers("/favicon.ico", "/error").permitAll()
                                .requestMatchers(
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(API + V1 + AUTH + LOGIN).permitAll()
                                .requestMatchers(API + V1 + AUTH + LOGIN+VERIFY).permitAll()
                                .requestMatchers(API + V1 + AUTH + REGISTER).permitAll()
                                .requestMatchers(API + V1 + AUTH + VERIFY).permitAll()

                                .requestMatchers(API + V1 + ATTACHMENT).permitAll()
                                .requestMatchers(API + V1 + ATTACHMENT + "/**").permitAll()

                                .requestMatchers(API + V1 + CATEGORY).permitAll()
                                .requestMatchers(API + V1 + BOOK).permitAll()
                                .requestMatchers(API + V1 + BOOK + "/*").permitAll()
                                .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(
//                List.of("http://localhost:5173", "http://13.60.252.171")
//        );       configuration.addAllowedMethod("*");
//        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedHeader("*");
//        configuration.setAllowCredentials(true); // Disable credentials for local testing
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ Allow only your trusted frontend origins (NO slash)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://13.60.252.171"
        ));

        // ✅ Explicitly allow HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Allow all headers
        configuration.setAllowedHeaders(List.of("*"));

        // ✅ Allow credentials (cookies, authorization headers, etc.)
        configuration.setAllowCredentials(true);

        // ✅ Register for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}