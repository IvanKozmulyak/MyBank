package com.mybank.service.impl;

import com.mybank.entity.Deposit;
import com.mybank.entity.User;
import com.mybank.exception.EntityNotFoundException;
import com.mybank.exception.NotEnoughMoneyException;
import com.mybank.repository.DepositRepository;
import com.mybank.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;

    private final UserServiceImpl userService;

    @Autowired
    public DepositServiceImpl(DepositRepository depositRepository, UserServiceImpl userService) {
        this.depositRepository = depositRepository;
        this.userService = userService;
    }


    /**
     * Method saves new deposit to Repository.
     * Saves only deposits which are validated.
     *
     * @param deposit deposit entity with info to be saved
     * @return saved deposit entity
     */
    @Override
     public Deposit save(Deposit deposit) {
        deposit.setCountOfTriggers(deposit.getDepositType().getTriggersCount());
        if (deposit.getUser().getBalance() < deposit.getAmount()){
            throw new NotEnoughMoneyException(deposit.getUser());
        }
        User user = deposit.getUser();
        user.setBalance(user.getBalance() - deposit.getAmount());
        userService.saveOrUpdate(user);
        return depositRepository.save(deposit);
    }

    /**
     * Method reduces the number of triggers
     * @param deposit deposit in which you need to reduce
     * @return deposit with a reduced number of triggers
     */
    public Deposit reduceCountOfTriggers(Deposit deposit) {
        deposit.setCountOfTriggers(deposit.getCountOfTriggers() - 1);
        return depositRepository.save(deposit);
    }

    /**
     * Method gets information about all deposits from Repository
     * @return List of all deposits
     */
    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    /**
     * Method deletes information about deposit from Repository
     */
    public void delete(Deposit deposit){
        userService.credit(deposit.getUser().getId(), deposit.getAmount());
        depositRepository.delete(deposit);
    }

    /**
     * Method checks count of trigger and delete deposit if it 0
     * @param deposit deposit we are checking
     */
    @Override
    public void checkCountOfTriggers(Deposit deposit) {
        if (deposit.getCountOfTriggers() == 0) {
            delete(deposit);
        }
    }

    /**
     * The method calculates and transfers interest to the account
     * @param deposit deposit on which the calculation of profit is made
     * @return amount of profit
     */
    @Override
    public Double transferInterest(Deposit deposit) {
        deposit = reduceCountOfTriggers(deposit);
        Double amount = getInterest(deposit.getAmount(), deposit.getDepositType().getPercentage());
        return userService.credit(deposit.getUser().getId(), amount);
    }

    // return percentage of the amount
    private Double getInterest(Double amount, Integer interest) {
        return (amount / 100. * interest);
    }
}
