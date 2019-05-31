package com.upf.etic.documentwithqr.error.exception;

public class RepositoryException extends Exception{

    private String errorMessage;

    public RepositoryException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

}
