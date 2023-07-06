package com.cg.sp.exceptionHandler;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String value, String msg) {
        super(value + ", " + msg);
    }

}
