package com.crud.library.exceptions;

public class TitleNotFoundException extends Exception {

    public TitleNotFoundException(Long id) {
        super("Title with id: " + id + " doesn't exist in database.");
    }
}
