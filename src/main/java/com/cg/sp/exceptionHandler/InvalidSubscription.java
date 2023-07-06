package com.cg.sp.exceptionHandler;

public class InvalidSubscription extends RuntimeException{
    public InvalidSubscription(String message) {
        super(message);
    }
}
