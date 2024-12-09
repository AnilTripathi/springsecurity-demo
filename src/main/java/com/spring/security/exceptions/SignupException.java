package com.spring.security.exceptions;

public class SignupException extends RuntimeException{
    public SignupException(String message){
        super(message);
    }
}
