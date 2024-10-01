package com.vestaChrono.cashingApp.repositories;

import com.vestaChrono.cashingApp.entities.SalaryAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryAccountRepository extends CrudRepository<SalaryAccount, Long> {
}
