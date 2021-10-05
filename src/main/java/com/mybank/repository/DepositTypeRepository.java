package com.mybank.repository;

import com.mybank.entity.DepositType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTypeRepository extends CrudRepository<DepositType, Long> {

}
