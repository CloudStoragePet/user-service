package org.brain.user_service.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {
    @Schema(description = "Email address", example = "email@example.com")
    private String email;
    @Schema(description = "JWT auth token", example = "randomsequenceofbytes123as213sdw3")
    private String token;
}
