package com.mindex.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.entities.Employee;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired private EmployeeRepository employeeRepository;

    @Test
    public void test() {
        Employee employee =
                employeeRepository
                        .findById(UUID.fromString("16a596ae-edd3-4847-99fe-c4518e82c86f"))
                        .orElse(null);
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
    }
}
