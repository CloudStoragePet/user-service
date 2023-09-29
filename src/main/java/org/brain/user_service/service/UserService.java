package org.brain.user_service.service;

import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
     User signUp(User user) throws EntityNotFoundException;
     Authentication logIn(User user) throws EntityNotFoundException;
}
