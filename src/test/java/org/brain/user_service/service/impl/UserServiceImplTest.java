package org.brain.user_service.service.impl;

import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.Role;
import org.brain.user_service.model.User;
import org.brain.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleServiceImpl roleService;

    Role roleUser;
    final Long roleUserId = 1L;
    final String roleUserName = "ROLE_USER";

    User user;
    final Long userId = 1L;
    final String userEmail = "email@example.com";
    final String userFirstName = "FirstName";
    final String userLastName = "Lastname";
    final boolean userEmailConfirmed = true;
    final String userPassword = "Password52";
    final String userPasswordEncoded = "Password52";
    Set<Role> userRoles;
    Set<User> roleUsers;
    @BeforeEach
    void setUpCorrectUser() {
        user = User.builder()
                .id(userId)
                .email(userEmail)
                .firstName(userFirstName)
                .lastName(userLastName)
                .emailConfirmed(userEmailConfirmed)
                .password(userPassword)
                .roles(userRoles)
                .build();
        roleUsers = Set.of(user);
    }
    @BeforeEach
    void setUpCorrectRole() {
        roleUser = Role.builder()
                .id(roleUserId)
                .name(roleUserName)
                .users(roleUsers)
                .build();
        userRoles = Set.of(roleUser);
    }
    @Test
    void signUp_ValidUser_Success() {

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(userPasswordEncoded);
        when(roleService.userRole()).thenReturn(roleUser);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userResult = userService.signUp(user);

        assertThat(userResult)
                .isNotNull()
                .satisfies(u -> {
                    assertThat(u.getEmail()).isEqualTo(user.getEmail());
                    assertThat(u.getPassword()).isEqualTo(userPasswordEncoded);
                    assertThat(u.getRoles()).isEqualTo(userRoles);
                    assertThat(u.getRoles()).isNotEmpty();
                });
        verify(userRepository).existsByEmail(user.getEmail());
        verify(passwordEncoder).encode(user.getPassword());
        verify(roleService).userRole();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signUp_ExistingEmail_EntityNotFoundException() {

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.signUp(user))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void logIn_ValidUser_Success() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()))).thenReturn(authentication);

        Authentication authenticationResult = userService.logIn(user);

        assertThat(authenticationResult)
                .isEqualTo(authentication);

        assertThat(authenticationResult).isSameAs(authentication);
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()));
    }
}