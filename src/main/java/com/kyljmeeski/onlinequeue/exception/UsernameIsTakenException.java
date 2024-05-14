package com.kyljmeeski.onlinequeue.exception;

public class UsernameIsTakenException extends RuntimeException {
    public UsernameIsTakenException(String message) {
        super(message);
    }
}
