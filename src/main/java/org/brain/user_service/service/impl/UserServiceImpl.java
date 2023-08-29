package org.brain.user_service.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.response.LoginResponse;
import org.brain.user_service.repository.UserRepository;
import org.brain.user_service.service.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record UserServiceImpl(UserRepository userRepository) implements UserService {
    private static final String ACCOUNT_ALREADY_EXISTS = "Account with this email already exists!";

    public User signUp(User user) throws EntityNotFoundException {
        // todo: check if email valid
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityNotFoundException(ACCOUNT_ALREADY_EXISTS);
        }
        // todo: encode password
        // todo: add roles
        // todo: confirm email
        return userRepository.save(user);
    }

    @Override
    public LoginResponse logIn(User user) throws EntityNotFoundException {
        log.info("processing login");
        //todo: provide token
        var token = "token";
        //todo: encode password
        User localUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new EntityNotFoundException(
                "User not found with email = " + user.getEmail()));
        if (!user.getPassword().equals(localUser.getPassword()))
            throw new EntityNotFoundException("Credentials not valid for email = " + user.getEmail());
        return new LoginResponse(localUser.getId(), user.getEmail(), token);
    }
}
