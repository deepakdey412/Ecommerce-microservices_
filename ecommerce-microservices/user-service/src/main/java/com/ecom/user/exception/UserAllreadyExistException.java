package com.ecom.user.exception;

public class UserAllreadyExistException extends Exception {
    public UserAllreadyExistException(String message) {
        super(message);
    }
}
