package com.crud.library.exceptions;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(Long id) {
        super("Book with id: " + id + " doesn't exist in database.");
    }
}
