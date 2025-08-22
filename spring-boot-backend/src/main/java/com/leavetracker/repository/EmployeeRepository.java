package com.leavetracker.repository;

import com.leavetracker.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findByAssociateId(Long associateId);
    Optional<Employee> findByName(String name);
    void deleteByAssociateId(Long associateId);
}
