package org.brain.user_service.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.brain.user_service.validation.EqualFields;
import org.brain.user_service.validation.groups.ValidationLogin;
import org.brain.user_service.validation.groups.ValidationRegistration;

@Builder
@Data
@EqualFields(message = "Passwords didn't match", value = {"password", "confirmPassword"}, groups =
        {ValidationRegistration.class})
public class UserRequest {
    /**
     * At least 1 digit.
     * At least 1 lowercase letter.
     * At least 1 uppercase letter.
     * No white-space characters or other symbol.
     * At least 8 characters long.
     */
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    @Schema(description = "Firstname", example = "John")
    @NotNull(groups = {ValidationRegistration.class})
    private String firstName;
    @Schema(description = "Lastname", example = "Doe")
    @NotNull(groups = {ValidationRegistration.class})
    private String lastName;
    @Schema(description = "Email", example = "email@example.com")
    @NotNull(groups = {ValidationRegistration.class, ValidationLogin.class})
    @Email(message = "Email not valid", groups = {ValidationRegistration.class, ValidationLogin.class})
    private String email;
    @Schema(description = "Password", example = "Password52")
    @NotEmpty(groups = {ValidationRegistration.class, ValidationLogin.class})
    @Pattern(message = "Password not valid", regexp = PASSWORD_PATTERN, groups = {ValidationRegistration.class, ValidationLogin.class})
    private String password;
    @Schema(description = "Confirm password", example = "Password52")
    @Pattern(message = "Confirm password not valid", regexp = PASSWORD_PATTERN)
    @NotNull(groups = {ValidationRegistration.class})
    private String confirmPassword;
}
