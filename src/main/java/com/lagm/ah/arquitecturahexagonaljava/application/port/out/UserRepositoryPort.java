package com.lagm.ah.arquitecturahexagonaljava.application.port.out;

import com.lagm.ah.arquitecturahexagonaljava.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
}
