package com.user.service.api.repositories;

import com.user.service.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    public Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> findByEmail(String email);
}
