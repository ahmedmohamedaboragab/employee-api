package employee_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                        "/api/auth/**"
                ).permitAll()

                .requestMatchers(
                        org.springframework.http.HttpMethod.GET,
                        "/api/employees/**"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.POST,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.PUT,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.DELETE,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.GET,
                        "/api/departments/**"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.POST,
                        "/api/departments/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.PUT,
                        "/api/departments/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        org.springframework.http.HttpMethod.DELETE,
                        "/api/departments/**"
                ).hasRole("ADMIN")

                .anyRequest().authenticated()
        );

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}