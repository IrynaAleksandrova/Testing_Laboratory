package com.example.proving_laboratory.exception;

public class StartProcessException extends RuntimeException {

    public StartProcessException() {
        super();
    }

    @Override
    public String getMessage() {
        return ExceptionConstant.CANT_START_PROCESS;
    }

}
