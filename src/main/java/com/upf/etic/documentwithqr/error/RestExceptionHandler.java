package com.upf.etic.documentwithqr.error;

import com.upf.etic.documentwithqr.error.exception.RepositoryException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RepositoryException.class)
    protected ResponseEntity<Object> filterByTypeNotFound(RepositoryException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getErrorMessage(), ex);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}