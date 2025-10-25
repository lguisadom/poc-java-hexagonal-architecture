package com.lagm.ah.arquitecturahexagonaljava.infrastructure.persistence;

import jakarta.persistence.*;

@Entity()
@Table(name = "users")
public class UserEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String firstName;
    private final String lastName;

    public UserEntity(
            Long id,
            String firstName,
            String lastName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
