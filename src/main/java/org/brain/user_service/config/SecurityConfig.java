package org.brain.user_service.config;

import lombok.RequiredArgsConstructor;
import org.brain.user_service.filter.JwtFilter;
import org.brain.user_service.service.impl.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
                // csrf creates token and hides it in html todo
                .csrf(AbstractHttpConfigurer::disable)
                // can use declarative way instead by @EnableMethodSecurity(securedEnabled = true), @Secured("User", "Admin")
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/auth/").permitAll()
                        // hasRole uses Authorization Manager, can implement custom todo
                        .requestMatchers("/api/v1/validate/admin").hasRole("ADMIN")
                        .requestMatchers("/api/v1/validate/user").hasRole("USER")
                        .anyRequest().authenticated())
                // sessionManagement checks if user can login into multiple devices simultaneously todo
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // exceptionHandling checks if user is redirected to access-denied page todo
                .exceptionHandling((ex) -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        // addFilterBefore
        return http.build();
    }

}
