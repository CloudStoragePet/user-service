package org.brain.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.brain.user_service.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final String noUserWithEmailErrorMessage = "No user with such email => ";

    public Optional<User> findByUserName(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = findByUserName(email).orElseThrow(() -> new EntityNotFoundException(noUserWithEmailErrorMessage + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }
}
