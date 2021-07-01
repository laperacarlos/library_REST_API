package com.crud.library.exceptions;

public class RentalNotFoundException extends Exception{

    public RentalNotFoundException(Long id) {
        super("Book with id: " + id + " doesn't have active rental.");
    }
}
