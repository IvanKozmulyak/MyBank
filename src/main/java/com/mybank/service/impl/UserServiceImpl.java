package com.mybank.service.impl;

import com.mybank.entity.User;
import com.mybank.exception.EntityNotFoundException;
import com.mybank.repository.UserRepository;
import com.mybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * When we do save on entity with empty id it will do a save.
     * When we do save on entity with existing id it will do an update
     *
     * @param user user you want to save or update
     * @return saved or updated user
     */
    @Override
    public User saveOrUpdate(User user){
        return userRepository.save(user);
    }

    /**
     * Method gets information from Repository for User with id parameter
     * @param id Identity number of the User
     * @return User entity
     */
    @Override
    public User findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException(User.class);
        }
    }

    /**
     * Method that credits money to the balance
     * @param id id of the account to which we need to credit the money
     * @param amount amount of money
     * @return
     */
    @Override
    public Double credit(Long id, Double amount){
        User user = findById(id);
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return amount;
    }
}
