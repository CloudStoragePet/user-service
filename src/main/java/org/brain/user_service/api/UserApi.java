package org.brain.user_service.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.brain.user_service.exceptionHandler.exceptions.ServiceException;
import org.brain.user_service.payload.request.UserRequest;
import org.brain.user_service.payload.response.UserResponse;
import org.brain.user_service.payload.response.LoginResponse;
import org.brain.user_service.validation.groups.ValidationLogin;
import org.brain.user_service.validation.groups.ValidationRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Authentication service", description = "Authentication management API")
@RequestMapping("/api/v1/auth")
@Validated
public interface UserApi {


    @Operation(summary = "Sign-up a default user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")})})
    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserResponse> signUp(@Validated({ValidationRegistration.class}) @RequestBody UserRequest request) throws ServiceException;

    @Operation(summary = "Log-in and get token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")})})
    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<LoginResponse> logIn(@Validated({ValidationLogin.class}) @RequestBody UserRequest request) throws ServiceException;

}
