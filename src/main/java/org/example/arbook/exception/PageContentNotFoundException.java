package org.example.arbook.exception;

public class PageContentNotFoundException extends RuntimeException {
    public PageContentNotFoundException(String message) {
        super(message);
    }
}
