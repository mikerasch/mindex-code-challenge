package com.mindex.challenge.dao;

import com.mindex.challenge.entities.Compensation;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, UUID> {
    @Query("{'employee.employeeId': ?0}")
    Set<Compensation> findAllByEmployeeId(UUID employeeId);
}
