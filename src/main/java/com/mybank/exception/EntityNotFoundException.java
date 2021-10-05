package com.mybank.exception;

import org.springframework.util.StringUtils;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(Class clazz) {
        super(StringUtils.capitalize(clazz.getSimpleName()) + " was not found");
    }
}
