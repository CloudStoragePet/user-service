package org.brain.user_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.api.ValidateApi;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.response.UserResponse;
import org.brain.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class ValidateController implements ValidateApi {
    private final UserService userService;
    @Override
    public ResponseEntity<UserResponse> validateUserToken(UserDetails userDetails) {
        log.info("User got validated");
        User user = userService.findByEmail(userDetails.getUsername());
        var validateTokenResponse = UserResponse.builder()
                .id(user.getId())
                .email(userDetails.getUsername())
                .build();
        log.debug("Users token is valid for principal: " + userDetails.getUsername());
        return new ResponseEntity<>(validateTokenResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> validateAdminToken(UserDetails userDetails) {
        log.info("Admin got validated");
        User user = userService.findByEmail(userDetails.getUsername());
        var validateTokenResponse = UserResponse.builder()
                .id(user.getId())
                .email(userDetails.getUsername())
                .build();
        log.debug("Admins token is valid for principal: " + userDetails.getUsername());
        return new ResponseEntity<>(validateTokenResponse, HttpStatus.OK);
    }
}
