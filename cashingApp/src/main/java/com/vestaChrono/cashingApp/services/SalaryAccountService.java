package com.vestaChrono.cashingApp.services;

import com.vestaChrono.cashingApp.entities.Employee;
import com.vestaChrono.cashingApp.entities.SalaryAccount;

public interface SalaryAccountService {

    void createAccount(Employee employee);

    SalaryAccount incrementSalary(Long accountId);
}
