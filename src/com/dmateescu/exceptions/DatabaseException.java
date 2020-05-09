package com.dmateescu.exceptions;

public class DatabaseException extends Throwable {
    @Override
    public String getMessage() {
        return "Music Database cannot be accessed!";
    }
}
