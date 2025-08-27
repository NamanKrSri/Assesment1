package com.SDEAssignment.demo.Repository;

import com.SDEAssignment.demo.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);
    Optional<User> findByEmail(String email);
}
