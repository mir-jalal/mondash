package net.mirjalal.mondash.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
