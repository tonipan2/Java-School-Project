package com.example.groupproject.repository;

import com.example.groupproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by password and username
    @Query("SELECT u FROM User u WHERE u.password = :password AND u.username = :username")
    Optional<User> findUserByPasswordAndUsername(String password, String username);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);
    boolean existsById(long userId);


}
