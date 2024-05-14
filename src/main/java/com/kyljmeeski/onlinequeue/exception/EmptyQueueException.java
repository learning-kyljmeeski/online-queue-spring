package com.kyljmeeski.onlinequeue.exception;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
        super("There is no one to call next");
    }
}
