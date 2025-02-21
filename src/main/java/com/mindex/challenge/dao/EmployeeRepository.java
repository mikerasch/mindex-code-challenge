package com.mindex.challenge.dao;

import com.mindex.challenge.entities.Employee;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, UUID> {}
