package org.brain.user_service.service;

import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.Role;

public interface RoleService {
     Role userRole() throws EntityNotFoundException;
     Role adminRole() throws EntityNotFoundException;
}
