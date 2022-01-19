package io.dmitrikonnov.DeanerySimpleSpringBootApp.Repositories;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAccountRepository extends JpaRepository <AccountEntity, Integer> {
}
