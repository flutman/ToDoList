package main.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ActionNotFoundException.class})
    public final ResponseEntity<ApiError> handleException(ActionNotFoundException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

//        if (ex instanceof ActionNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ActionNotFoundException enfe = (ActionNotFoundException) ex;
            return handleEntityNotFoundException(enfe, headers, status, request);
//        } else {
//            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//            return handleExceptionInternal(ex, null, headers, status, request);
//        }
    }

    protected ResponseEntity<ApiError> handleEntityNotFoundException(ActionNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE,ex,WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
