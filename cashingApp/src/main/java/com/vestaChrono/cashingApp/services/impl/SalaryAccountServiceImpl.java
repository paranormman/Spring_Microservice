package com.vestaChrono.cashingApp.services.impl;

import com.vestaChrono.cashingApp.entities.Employee;
import com.vestaChrono.cashingApp.entities.SalaryAccount;
import com.vestaChrono.cashingApp.repositories.SalaryAccountRepository;
import com.vestaChrono.cashingApp.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;

    @Override
    public void createAccount(Employee employee) {

//        if (employee.getName().equals("Manoj")) throw new RuntimeException("Manoj is not allowed");

        SalaryAccount salaryAccount = SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();

        salaryAccountRepository.save(salaryAccount);

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalaryAccount incrementSalary(Long accountId) {
//        find the account with id
        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
//        get the previous balance and increment the value
        BigDecimal prevBalance = salaryAccount.getBalance();
        BigDecimal newBalance = prevBalance.add(BigDecimal.valueOf(1L));
//        set the new balance to the account
        salaryAccount.setBalance(newBalance);
//        save the updated salary account
        return salaryAccountRepository.save(salaryAccount);
    }
}
