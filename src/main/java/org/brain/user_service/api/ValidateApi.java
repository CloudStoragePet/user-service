package org.brain.user_service.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Authentication service", description = "Authentication management API")
@RequestMapping("/api/v1/validate")
public interface ValidateApi {


    @Operation(summary = "Validate user token", tags = { "Validate" })
    @PostMapping(value = "/validate/user")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> validateUserToken();
    @Operation(summary = "Confirm acceses to user account", tags = { "Validate" })
    @PostMapping(value = "/validate/admin")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> validateAdminToken();
}
