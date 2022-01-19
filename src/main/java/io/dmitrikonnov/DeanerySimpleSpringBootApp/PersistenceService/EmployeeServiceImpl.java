package io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.EmployeeEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final static Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final static String SQL_CONSTRAINT_VIOLATION_MSG = "SQLIntegrityConstraintViolationException has occurred. %s";

    @Override
    public void addEmployee(EmployeeEntity employeeEntity) throws SQLIntegrityConstraintViolationException {


        try {
            employeeRepository.save(employeeEntity);
        } catch (Exception e) {
            if(e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException sql_violation_exception = (SQLIntegrityConstraintViolationException) e.getCause().getCause() ;
            log.error(String.format(SQL_CONSTRAINT_VIOLATION_MSG, sql_violation_exception));
            throw  sql_violation_exception;
        } else {
            log.error(e.getMessage());
        }}
    }


    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
