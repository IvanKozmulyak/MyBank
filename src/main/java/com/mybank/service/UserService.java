package com.mybank.service;

import com.mybank.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveOrUpdate(User user);
    User findById(Long id);
    Double credit(Long id, Double amount);
}
