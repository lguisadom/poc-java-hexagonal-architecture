package com.lagm.ah.arquitecturahexagonaljava.domain.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
