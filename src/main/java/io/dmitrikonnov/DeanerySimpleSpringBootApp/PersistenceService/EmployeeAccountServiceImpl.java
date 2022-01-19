package io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.AccountEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Repositories.EmployeeAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeAccountServiceImpl implements EmployeeAccountService {


    private final EmployeeAccountRepository employeeAccountRepository;
    @Override
    public void addEmployeeAccount(AccountEntity accountEntity) {
        employeeAccountRepository.save(accountEntity);
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return employeeAccountRepository.findAll();
    }
}
