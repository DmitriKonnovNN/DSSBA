package io.dmitrikonnov.DeanerySimpleSpringBootApp.controller;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.AccountEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService.EmployeeAccountService;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {


    private final EmployeeAccountService employeeAccountService;

    @Autowired
    public AccountController (EmployeeAccountService employeeAccountService) {
        this.employeeAccountService = employeeAccountService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccount (@RequestDTO(AccountEntity.class) @Validated AccountEntity accountEntity){
        employeeAccountService.addEmployeeAccount(accountEntity);
    }

    @GetMapping("/all")
    public List<AccountEntity> getAllAccounts (){
        return employeeAccountService.getAllAccounts();
    }
}
