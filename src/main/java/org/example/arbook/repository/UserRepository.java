package org.example.arbook.repository;

import org.example.arbook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM users WHERE phone_number = :phoneNumber", nativeQuery = true)
    Optional<User>  findByPhoneNumberOptional(@Param("phoneNumber") String phoneNumber);

}