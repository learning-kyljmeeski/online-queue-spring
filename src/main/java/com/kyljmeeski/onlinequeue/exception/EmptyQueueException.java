package com.kyljmeeski.onlinequeue.exception;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException(String message) {
        super(message);
    }
}
