package com.lagm.ah.arquitecturahexagonaljava.application.service;

import com.lagm.ah.arquitecturahexagonaljava.application.port.out.UserRepositoryPort;
import com.lagm.ah.arquitecturahexagonaljava.domain.exception.InvalidUserDataException;
import com.lagm.ah.arquitecturahexagonaljava.domain.exception.UserNotFoundException;
import com.lagm.ah.arquitecturahexagonaljava.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        User user = new User(null, "John", "Doe");
        User savedUser = new User(1L, "John", "Doe");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = userService.createUser(user);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        verify(userRepository).save(user);
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "John", "Doe");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = userService.findById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenCreatingUserWithInvalidData() {
        // When & Then - The exception is thrown during User construction, not in the service
        assertThrows(InvalidUserDataException.class, () -> new User(null, "", "Doe"));
        verify(userRepository, never()).save(any());
    }
}