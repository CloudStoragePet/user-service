package org.brain.user_service.controller;

import org.brain.user_service.config.SecurityConfig;
import org.brain.user_service.model.User;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ValidateController.class)
@Import({SecurityConfig.class})
class ValidateControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MyUserDetailsService myUserDetailsService;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserService userService;
    private static final String VALIDATE_URL = "/api/v1/validation/";
    final Long userId = 1L;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void validateUserToken_WithValidUser_Success() throws Exception {
        when(userService.findByEmail(any(String.class))).thenReturn(User.builder().id(userId).email("user").build());
        mockMvc.perform(post(VALIDATE_URL + "user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("user"))
                .andExpect(jsonPath("$.id")
                        .value(userId));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void validateAdminToken_WithNotValidUser_Forbidden() throws Exception {
        mockMvc.perform(post(VALIDATE_URL + "admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void validateAdminToken_WithValidAdmin_Success() throws Exception {
        when(userService.findByEmail(any(String.class))).thenReturn(User.builder().id(userId).email("admin").build());
        mockMvc.perform(post(VALIDATE_URL + "admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("admin"))
                .andExpect(jsonPath("$.id")
                        .value(userId));
    }
}