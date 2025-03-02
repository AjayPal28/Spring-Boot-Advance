package com.ajay.services;

import com.ajay.entities.Employee;
import com.ajay.entities.SalaryAccount;

public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}
