package org.brain.user_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.api.UserApi;
import org.brain.user_service.exceptionHandler.exceptions.ServiceException;
import org.brain.user_service.mapper.UserMapper;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.response.LoginResponse;
import org.brain.user_service.payload.request.UserRequest;
import org.brain.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public record UserController(UserService userService) implements UserApi {
    @PostMapping
    public ResponseEntity<User> signUp( UserRequest request) throws ServiceException {
        log.info("new user registration {}", request);
        User user = UserMapper.INSTANCE.mapToUser(request);
        User persistedUser = userService.signUp(user);
        return new ResponseEntity<>(persistedUser, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginResponse> logIn(UserRequest request) throws ServiceException {
        log.info("request for login");
        User user = UserMapper.INSTANCE.mapToUser(request);
        LoginResponse response = userService.logIn(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
