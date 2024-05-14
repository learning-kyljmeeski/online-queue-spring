package com.kyljmeeski.onlinequeue.exception;

public class UsernameIsTakenException extends RuntimeException {
    public UsernameIsTakenException(String username) {
        super("Username `" + username + "` is already in use");
    }
}
