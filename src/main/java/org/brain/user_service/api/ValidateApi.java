package org.brain.user_service.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.brain.user_service.payload.response.EmailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Validate service", description = "Validate JWT management API")
@RequestMapping("/api/v1/validation")
public interface ValidateApi {


    @Operation(summary = "Validate user token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User token is valid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailResponse.class))})
    })
    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<EmailResponse> validateUserToken(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "Validate admin token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User token is valid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailResponse.class))})
    })
    @PostMapping(value = "/admin")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<EmailResponse> validateAdminToken(@AuthenticationPrincipal UserDetails userDetails);
}
