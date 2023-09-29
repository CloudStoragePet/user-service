package org.brain.user_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.api.ValidateApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public record ValidateController () implements ValidateApi {

    @Override
    public ResponseEntity<Void> validateUserToken() {
        log.info("User got validated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> validateAdminToken() {
        log.info("Admin got validated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
