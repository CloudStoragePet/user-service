package org.brain.user_service.service.impl;

import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.Role;
import org.brain.user_service.repository.RoleRepository;
import org.brain.user_service.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public record RoleServiceImpl (RoleRepository roleRepository) implements RoleService {
    private static final String USER_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_USER";
    @Override
    public Role userRole() throws EntityNotFoundException {
        return roleRepository.findByName(USER_ROLE).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Role adminRole() throws EntityNotFoundException {
        return roleRepository.findByName(ADMIN_ROLE).orElseThrow(EntityNotFoundException::new);

    }
}
