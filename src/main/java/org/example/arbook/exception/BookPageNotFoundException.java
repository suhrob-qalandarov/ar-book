package org.example.arbook.exception;

public class BookPageNotFoundException extends RuntimeException {
    public BookPageNotFoundException(String message) {
        super(message);
    }
}
