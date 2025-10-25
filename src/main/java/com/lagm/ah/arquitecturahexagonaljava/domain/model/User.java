package com.lagm.ah.arquitecturahexagonaljava.domain.model;

import com.lagm.ah.arquitecturahexagonaljava.domain.exception.InvalidUserDataException;

public record User(
    Long id,
    String firstName,
    String lastName
) {
    public User {
        validateUserData(firstName, lastName);
    }
    
    private static void validateUserData(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidUserDataException("User first name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidUserDataException("User last name cannot be null or empty");
        }
    }
}
