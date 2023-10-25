package org.brain.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.brain.user_service.config.SecurityConfig;
import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.request.UserRequest;
import org.brain.user_service.service.UserService;
import org.brain.user_service.service.impl.MyUserDetailsService;
import org.brain.user_service.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import({SecurityConfig.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    MyUserDetailsService myUserDetailsService;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String SIGH_UP_URL = "/api/v1/auth/signup";
    final Long userId = 1L;
    final String userEmail = "email@example.com";
    final String userFirstName = "FirstName";
    final String userLastName = "Lastname";
    final boolean userEmailConfirmed = true;
    final String userPassword = "Password52";
    final String userPasswordSecond = "Password5233";
    final String userPasswordEncoded = "Password52";

    @Test
    public void signUp_withValidRequest_returnsEmailResponseAndCreatedStatus() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(userEmail)
                .password(userPassword)
                .confirmPassword(userPasswordEncoded)
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();
        when(userService.signUp(any(User.class))).thenReturn(User.builder().id(userId).email(userEmail).build());

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(userEmail));
    }

    @Test
    public void signUp_withEmailExists_returnsBadRequest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(userEmail)
                .password(userPassword)
                .confirmPassword(userPasswordEncoded)
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();
        when(userService.signUp(any(User.class))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUp_withEmptyEmail_returnsBadRequest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("")
                .password(userPassword)
                .confirmPassword(userPasswordEncoded)
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUp_withNotValidEmail_returnsBadRequest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("NotValidEmail")
                .password(userPassword)
                .confirmPassword(userPasswordEncoded)
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUp_withEmptyPassword_returnsBadRequest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(userEmail)
                .password("")
                .confirmPassword("")
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUp_withNotValidPassword_returnsBadRequest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(userEmail)
                .password("NotValidPassword")
                .confirmPassword("NotValidPassword")
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUp_withPasswordsDontMatch_returnsBadRequest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email(userEmail)
                .password(userPassword)
                .confirmPassword(userPasswordSecond)
                .firstName(userFirstName)
                .lastName(userLastName)
                .build();

        mockMvc.perform(post(SIGH_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }


}