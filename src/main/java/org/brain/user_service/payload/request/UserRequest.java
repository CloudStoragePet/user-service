package org.brain.user_service.payload.request;

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
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    @NotNull(groups = {ValidationRegistration.class})
    private String firstName;
    @NotNull(groups = {ValidationRegistration.class})
    private String lastName;
    @NotNull(groups = {ValidationRegistration.class, ValidationLogin.class})
    @Email(message = "Email not valid", groups = {ValidationRegistration.class, ValidationLogin.class})
    private String email;
    @NotEmpty(groups = {ValidationRegistration.class, ValidationLogin.class})
    @Pattern(message = "Password not valid", regexp = PASSWORD_PATTERN, groups = {ValidationRegistration.class, ValidationLogin.class})
    private String password;
    @Pattern(message = "Confirm password not valid", regexp = PASSWORD_PATTERN)
    @NotNull(groups = {ValidationRegistration.class})
    private String confirmPassword;
}
