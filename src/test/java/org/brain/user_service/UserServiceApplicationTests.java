package org.brain.user_service;

import org.brain.user_service.payload.request.UserRequest;
import org.brain.user_service.utils.JwtUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private JwtUtils jwtUtils;
    // endpoint
    private static final String SIGN_UP_URI = "/api/v1/auth/signup";
    private static final String LOG_IN_URI = "/api/v1/auth/login";
    private static final String VALIDATION_USER_URI = "/api/v1/validation/user";
    private static final String VALIDATION_ADMIN_URI = "/api/v1/validation/admin";


    // data for tests
    final Long userId = 1L;
    final String userEmail = "email@example.com";
    final String adminEmail = "admin@example.com";
    final String userFirstName = "FirstName";
    final String userLastName = "Lastname";
    final boolean userEmailConfirmed = true;
    final String userPassword = "Password52";
    final String userPasswordSecond = "Password5233";
    final String userPasswordEncoded = "Password52";

    final String userRoleName = "ROLE_USER";
    final String adminRoleName = "ROLE_ADMIN";
    final List<String> userRoles = List.of(userRoleName);
    final List<String> adminRoles = List.of(userRoleName, adminRoleName);

    @Test
    @Order(1)
    void signUp_User_Success() {

        UserRequest signUpRequest = UserRequest.builder()
                .email(userEmail)
                .firstName(userFirstName)
                .lastName(userLastName)
                .password(userPassword)
                .confirmPassword(userPassword)
                .build();

        webTestClient.post()
                .uri(SIGN_UP_URI)
                .bodyValue(signUpRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(userEmail);

    }

    @Test
    @Order(2)
    void logIn_User_Success() {
        UserRequest logInRequest = UserRequest.builder()
                .email(userEmail)
                .password(userPassword)
                .build();

        webTestClient.post()
                .uri(LOG_IN_URI)
                .bodyValue(logInRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(userEmail);
    }

    @Test
    void validateUserToken_User_Success() {
        String token = generateJwtToken(userEmail, userRoles);
        webTestClient
                .post()
                .uri(VALIDATION_USER_URI)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(userEmail);
    }

    @Test
    void validateUserToken_Admin_Success() {
        String token = generateJwtToken(adminEmail, adminRoles);
        webTestClient
                .post()
                .uri(VALIDATION_USER_URI)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(adminEmail);
    }

    @Test
    void validateAdminToken_Admin_Success() {
        String token = generateJwtToken(adminEmail, adminRoles);
        webTestClient
                .post()
                .uri(VALIDATION_ADMIN_URI)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(adminEmail);
    }

    @Test
    void validateAdminToken_User_Unauthorized() {
        String token = generateJwtToken(userEmail, userRoles);
        webTestClient
                .post()
                .uri(VALIDATION_ADMIN_URI)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody();

    }

    public String generateJwtToken(String username, List<String> roles) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
        UserDetails userDetails = new User(username, userPasswordEncoded, simpleGrantedAuthorities);
        return jwtUtils.generateToken(userDetails);
    }
}
