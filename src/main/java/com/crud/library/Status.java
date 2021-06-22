package com.crud.library;

public enum Status {
    AVAILABLE("available"),
    RENTED("rented"),
    DESTROYED("destroyed"),
    LOST("lost");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
