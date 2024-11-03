package org.sopt.diary.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserEntity,Long> {
    Optional<UserEntity> findByUsername(String name);

    boolean existsByNickname(String nickname);
}