package com.example.testing_laboratory.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException() {
        super();
    }

    @Override
    public String getMessage() {
        return ExceptionConstant.CLIENT_NOT_FOUND;
    }

}
