package org.example.arbook.exception;

public class AttachmentNotFoundException extends RuntimeException {
    public AttachmentNotFoundException(String message) {
        super(message);
    }
}
