package com.example.proving_laboratory.exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException() {
        super();
    }

    @Override
    public String getMessage() {
        return ExceptionConstant.INCORRECT_PASSWORD;
    }
}
