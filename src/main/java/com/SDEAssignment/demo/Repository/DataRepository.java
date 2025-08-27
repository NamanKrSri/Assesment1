package com.SDEAssignment.demo.Repository;

import com.SDEAssignment.demo.Entity.User;
import com.SDEAssignment.demo.Entity.UserData;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUser(User user);
}
