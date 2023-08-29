package org.brain.user_service.service;

import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.response.LoginResponse;

public interface UserService {
     User signUp(User user) throws EntityNotFoundException;
     LoginResponse logIn(User user) throws EntityNotFoundException;
}
