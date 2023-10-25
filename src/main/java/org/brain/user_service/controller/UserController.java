package org.brain.user_service.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.api.UserApi;
import org.brain.user_service.mapper.UserMapper;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.request.UserRequest;
import org.brain.user_service.payload.response.UserResponse;
import org.brain.user_service.payload.response.LoginResponse;
import org.brain.user_service.service.UserService;
import org.brain.user_service.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController implements UserApi {
    private UserService userService;
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<UserResponse> signUp(UserRequest request) {
        log.info("new user registration {}", request);
        User user = UserMapper.INSTANCE.mapToUser(request);
        user = userService.signUp(user);
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail()).build();
        // todo: confirm email
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginResponse> logIn(UserRequest request) {
        log.info("request for login");
        User user = UserMapper.INSTANCE.mapToUser(request);
        Authentication authentication = userService.logIn(user);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);
        user = userService.findByEmail(userDetails.getUsername()); // get id
        log.debug("user logged in: " + userDetails.getUsername());
        LoginResponse response = LoginResponse.builder()
                .id(user.getId())
                .email(userDetails.getUsername())
                .token(token)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
