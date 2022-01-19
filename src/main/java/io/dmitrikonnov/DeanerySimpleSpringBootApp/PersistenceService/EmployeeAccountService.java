package io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.AccountEntity;

import java.util.List;

public interface EmployeeAccountService {
    public void addEmployeeAccount (AccountEntity accountEntity);
    public List <AccountEntity> getAllAccounts();
}
