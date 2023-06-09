package com.tworld.celebring.user.repository;

import com.tworld.celebring.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    <T extends User> T save(T user);
    Optional<User> findByOauthId(String oauthId);
}
