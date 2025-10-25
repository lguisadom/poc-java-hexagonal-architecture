package com.lagm.ah.arquitecturahexagonaljava.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
}
