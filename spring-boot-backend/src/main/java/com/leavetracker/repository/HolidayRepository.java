package com.leavetracker.repository;

import com.leavetracker.model.Holiday;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HolidayRepository extends MongoRepository<Holiday, String> {
}
