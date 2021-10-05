package com.mybank.service;

import com.mybank.entity.Deposit;

import java.util.List;

public interface DepositService {
    Deposit save(Deposit deposit);
    List<Deposit> findAll();
    void checkCountOfTriggers(Deposit deposit);
    Double transferInterest(Deposit deposit);
}
