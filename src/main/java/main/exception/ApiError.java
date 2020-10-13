package main.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private int code;
    private String message;
//    private List<ActionNotFoundException> entityNotFoundExceptionList;

    private List<String> errors;

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public ApiError(int code, String message, List<String> errors) {
        this.code = code;
        this.message = message;
//        this.entityNotFoundExceptionList = entityNotFoundExceptionList;
        this.errors = errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    private void addError(String message) {
        errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}
