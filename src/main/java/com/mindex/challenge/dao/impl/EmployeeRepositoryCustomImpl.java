package com.mindex.challenge.dao.impl;

import com.mindex.challenge.dao.EmployeeRepositoryCustom;
import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.entities.Employee;
import java.util.Set;
import java.util.UUID;

import jakarta.annotation.Nonnull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Custom repository for needs where MongoRepository implemented classes fall short.
 * Working with mongo template allows for more flexibility in our queries.
 */
@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public EmployeeRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Adds a set of compensations to an employee in the database.
     *
     * @param employeeId Unique Identifier for employee collection
     * @param compensations A set of {@link Compensation} objects to be added to the employee's compensations.
     */
    @Override
    public void addCompensationsToEmployee(@Nonnull UUID employeeId, @Nonnull Set<Compensation> compensations) {
        Query query = new Query(Criteria.where("employeeId").is(employeeId));

        Update update = new Update().push("compensations").each(compensations.toArray());

        mongoTemplate.updateFirst(query, update, Employee.class);
    }
}
