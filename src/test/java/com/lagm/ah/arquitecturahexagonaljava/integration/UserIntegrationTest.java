package com.lagm.ah.arquitecturahexagonaljava.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lagm.ah.arquitecturahexagonaljava.infrastructure.controller.dto.UserRequest;
import com.lagm.ah.arquitecturahexagonaljava.infrastructure.controller.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class UserIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldCreateAndRetrieveUser() throws Exception {
        // Given
        UserRequest userRequest = new UserRequest("John", "Doe");

        // When - Create user
        MvcResult createResult = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse createdUser = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                UserResponse.class);

        // Then
        assertNotNull(createdUser.id());
        assertEquals("John", createdUser.firstName());
        assertEquals("Doe", createdUser.lastName());

        // When - Retrieve user
        MvcResult getResult = mockMvc.perform(get("/users/{id}", createdUser.id()))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse retrievedUser = objectMapper.readValue(
                getResult.getResponse().getContentAsString(),
                UserResponse.class);

        // Then
        assertEquals(createdUser.id(), retrievedUser.id());
        assertEquals("John", retrievedUser.firstName());
        assertEquals("Doe", retrievedUser.lastName());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowExceptionWhenUserDataIsInvalid() throws Exception {
        // Given
        UserRequest invalidUserRequest = new UserRequest("", "Doe");

        // When & Then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest());
    }
}
