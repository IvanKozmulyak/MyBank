package com.mybank.exception;

import com.mybank.entity.User;

public class NotEnoughMoneyException extends RuntimeException{

    public NotEnoughMoneyException(User user) {
        super("User with name " + user.getName() + " does not have enough money on the balance");
    }
}
