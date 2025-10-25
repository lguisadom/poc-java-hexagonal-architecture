package com.lagm.ah.arquitecturahexagonaljava.infrastructure.persistence;

import com.lagm.ah.arquitecturahexagonaljava.application.port.out.UserRepositoryPort;
import com.lagm.ah.arquitecturahexagonaljava.domain.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;

    public JpaUserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user.id(), user.firstName(), user.lastName());
        final UserEntity savedUser = this.springDataUserRepository.save(userEntity);
        return new User(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName());
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.springDataUserRepository.findById(id)
            .map(userEntity -> new User(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName()));
    }
}
