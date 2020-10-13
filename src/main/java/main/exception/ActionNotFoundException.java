package main.exception;

import javax.persistence.EntityNotFoundException;

public class ActionNotFoundException extends EntityNotFoundException {

    public ActionNotFoundException(String message) {
        super(message);
    }
}
