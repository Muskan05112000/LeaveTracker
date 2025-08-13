package com.leavetracker.repository;

import com.leavetracker.model.Leave;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeaveRepository extends MongoRepository<Leave, String> {
}
