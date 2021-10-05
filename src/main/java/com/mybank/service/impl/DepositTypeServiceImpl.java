package com.mybank.service.impl;

import com.mybank.entity.DepositType;
import com.mybank.entity.User;
import com.mybank.exception.EntityNotFoundException;
import com.mybank.repository.DepositTypeRepository;
import com.mybank.service.DepositTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepositTypeServiceImpl implements DepositTypeService {

    private final DepositTypeRepository depositTypeRepository;

    @Autowired
    public DepositTypeServiceImpl(DepositTypeRepository depositTypeRepository) {
        this.depositTypeRepository = depositTypeRepository;
    }

    /**
     * Method gets information from Repository for DepositType with id parameter
     * @param id Identity number of the DepositType
     * @return DepositType entity
     */
    @Override
    public DepositType findById(Long id) {
        Optional<DepositType> depositType = depositTypeRepository.findById(id);
        if (depositType.isPresent()) {
            return depositType.get();
        } else {
            throw new EntityNotFoundException(User.class);
        }
    }
}
