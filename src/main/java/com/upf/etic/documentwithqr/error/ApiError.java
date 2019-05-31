package com.upf.etic.documentwithqr.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private String debugMessage;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setStatus(HttpStatus status){
        this.status = status;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

    public void setStatusCode(int status){
        this.statusCode = status;
    }

    public int getStatusCode(){
        return this.status.value();
    }

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}