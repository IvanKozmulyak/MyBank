package com.mybank.repository;

import com.mybank.entity.Deposit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long> {
    List<Deposit> findAll();
}
