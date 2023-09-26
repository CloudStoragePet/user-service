package org.brain.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.brain.user_service.model.User;
import org.brain.user_service.repository.RoleRepository;
import org.brain.user_service.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Optional<User> findByUserName(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByUserName(email).orElseThrow(() -> new UsernameNotFoundException("No user was found with " +
                "email " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    public void createNewUser(User user){
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER").get()));
        userRepository.save(user);
    }
}
