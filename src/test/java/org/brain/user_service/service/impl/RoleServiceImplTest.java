package org.brain.user_service.service.impl;

import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.Role;
import org.brain.user_service.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    Role roleUser;
    final Long roleUserId = 1L;

    Role roleAdmin;
    final Long roleAdminId = 2L;

    final String USER_ROLE = "ROLE_USER";
    final String ADMIN_ROLE = "ROLE_USER";

    @BeforeEach
    void setUpCorrectRole() {
        roleUser = Role.builder()
                .id(roleUserId)
                .name(USER_ROLE)
                .build();
        roleAdmin = Role.builder()
                .id(roleAdminId)
                .name(ADMIN_ROLE)
                .build();
    }

    @Test
    void userRole_FindsUserRoleInDB_Success() {
        when(roleRepository.findByName(USER_ROLE)).thenReturn(Optional.of(roleUser));

        Role roleResult = roleService.userRole();

        assertThat(roleResult)
                .isEqualTo(roleUser);
        verify(roleRepository).findByName(USER_ROLE);
    }

    @Test
    void userRole_NotFindsUserRoleInDB_Exception() {
        when(roleRepository.findByName(USER_ROLE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roleService.userRole())
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void adminRole_FindsAdminRoleInDB_Success() {
        when(roleRepository.findByName(ADMIN_ROLE)).thenReturn(Optional.of(roleAdmin));

        Role roleResult = roleService.adminRole();

        assertThat(roleResult)
                .isEqualTo(roleAdmin);
        verify(roleRepository).findByName(ADMIN_ROLE);
    }

    @Test
    void adminRole_NotFindsAdminRoleInDB_Exception() {
        when(roleRepository.findByName(ADMIN_ROLE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roleService.adminRole())
                .isInstanceOf(EntityNotFoundException.class);
    }
}