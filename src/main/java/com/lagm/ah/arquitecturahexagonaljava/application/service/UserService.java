package com.lagm.ah.arquitecturahexagonaljava.application.service;

import com.lagm.ah.arquitecturahexagonaljava.application.port.in.CreateUserUseCase;
import com.lagm.ah.arquitecturahexagonaljava.application.port.in.GetUserUseCase;
import com.lagm.ah.arquitecturahexagonaljava.application.port.out.UserRepositoryPort;
import com.lagm.ah.arquitecturahexagonaljava.domain.exception.UserNotFoundException;
import com.lagm.ah.arquitecturahexagonaljava.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CreateUserUseCase, GetUserUseCase {
    private final UserRepositoryPort userRepository;

    public UserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}
