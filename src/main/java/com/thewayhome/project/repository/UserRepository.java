package com.thewayhome.project.repository;


import com.thewayhome.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    List<User> findAll();
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndProvider(String username, String provider);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByRefreshToken(String refreshToken);
}
