package com.leavetracker.repository;

import com.leavetracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByAssociateId(Integer associateId);
    Optional<User> findByUsername(String username);
    void deleteByAssociateId(Integer associateId);
}
