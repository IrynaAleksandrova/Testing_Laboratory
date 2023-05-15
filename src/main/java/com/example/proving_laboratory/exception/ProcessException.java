package com.example.proving_laboratory.exception;

public class ProcessException extends RuntimeException {

    public ProcessException() {
        super();
    }

    @Override
    public String getMessage() {
        return ExceptionConstant.INCORRECT_PROCESS;
    }

}
