package com.lagm.ah.arquitecturahexagonaljava.application.port.in;

import com.lagm.ah.arquitecturahexagonaljava.domain.model.User;

public interface CreateUserUseCase {
    User createUser(User user);
}
