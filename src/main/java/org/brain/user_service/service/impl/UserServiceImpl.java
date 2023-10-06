package org.brain.user_service.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.brain.user_service.repository.UserRepository;
import org.brain.user_service.service.RoleService;
import org.brain.user_service.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public record UserServiceImpl(UserRepository userRepository,
                              AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder, RoleService roleService) implements UserService {


    private static final String ACCOUNT_ALREADY_EXISTS = "Account with this email already exists!";

    public User signUp(User user) throws EntityNotFoundException {
        log.info("processing signUp");
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error(ACCOUNT_ALREADY_EXISTS);
            throw new EntityNotFoundException(ACCOUNT_ALREADY_EXISTS);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleService.userRole()));
        return userRepository.save(user);
    }

    @Override
    public Authentication logIn(User user) throws EntityNotFoundException {
        log.info("processing login");
        // todo provide exceptions handling
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        ));
    }
}
