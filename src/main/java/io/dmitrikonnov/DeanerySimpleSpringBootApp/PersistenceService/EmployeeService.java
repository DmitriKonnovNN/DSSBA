package io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.EmployeeEntity;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface EmployeeService {

    void addEmployee (EmployeeEntity employeeEntity) throws SQLIntegrityConstraintViolationException;
    public List<EmployeeEntity> getAllEmployees ();
}
