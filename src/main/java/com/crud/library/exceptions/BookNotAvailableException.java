package com.crud.library.exceptions;

public class BookNotAvailableException extends Exception {

    public BookNotAvailableException(String message) {
        super(message);
    }
}
