package com.mindex.challenge.dao.impl;

import com.mindex.challenge.dao.EmployeeRepositoryCustom;
import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.entities.Employee;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public EmployeeRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void addCompensationsToEmployee(UUID employeeId, Set<Compensation> compensations) {
        Query query = new Query(Criteria.where("employeeId").is(employeeId));

        Update update = new Update().push("compensations").each(compensations.toArray());

        mongoTemplate.updateFirst(query, update, Employee.class);
    }
}
