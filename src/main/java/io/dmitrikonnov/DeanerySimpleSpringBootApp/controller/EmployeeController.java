package io.dmitrikonnov.DeanerySimpleSpringBootApp.controller;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.EmployeeDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.EmployeeEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService.EmployeeService;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.RequestDTO;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.UniqueConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping ("api/v1/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping (value = "/")
    @ResponseStatus (HttpStatus.CREATED)
    public void addEmployee ( @Validated @RequestDTO(EmployeeDto.class) EmployeeEntity employeeEntity){
        try { employeeService.addEmployee(employeeEntity);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UniqueConstraintException(e.getMessage());
        }
    }

    @GetMapping ("/all")
    public List<EmployeeEntity> getAllEmployees (){
        return employeeService.getAllEmployees();
    }


}
