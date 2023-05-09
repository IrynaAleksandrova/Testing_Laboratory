package com.example.proving_laboratory.exception;

public class SaveException extends RuntimeException {

    public SaveException() {
        super();
    }

    @Override
    public String getMessage() {
        return ExceptionConstant.OBJECT_PRESENT_IN_BASE;
    }
}
