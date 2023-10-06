package org.brain.user_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.api.ValidateApi;
import org.brain.user_service.payload.response.EmailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public record ValidateController() implements ValidateApi {
    @Override
    public ResponseEntity<EmailResponse> validateUserToken(UserDetails userDetails) {
        log.info("User got validated");
        var validateTokenResponse = EmailResponse.builder()
                .email(userDetails.getUsername())
                .build();
        log.debug("Users token is valid for principal: " + userDetails.getUsername());
        return new ResponseEntity<>(validateTokenResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmailResponse> validateAdminToken(UserDetails userDetails) {
        log.info("Admin got validated");
        var validateTokenResponse = EmailResponse.builder()
                .email(userDetails.getUsername())
                .build();
        log.debug("Admins token is valid for principal: " + userDetails.getUsername());
        return new ResponseEntity<>(validateTokenResponse, HttpStatus.OK);
    }
}
