package com.cg.sp.exceptionHandler;

public class InvalidDateRange extends RuntimeException{
    public InvalidDateRange(String msg) {
        super(msg);
    }
}
