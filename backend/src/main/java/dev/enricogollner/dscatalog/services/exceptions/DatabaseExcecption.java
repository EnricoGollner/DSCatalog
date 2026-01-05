package dev.enricogollner.dscatalog.services.exceptions;

public class DatabaseExcecption extends RuntimeException {
    public DatabaseExcecption(String message) {
        super(message);
    }
}
