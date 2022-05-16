package com.example.recetas.recetas.exception;

public class ApiError {

    private Boolean error = true;
    private String message= "Text not found";
    private Integer code = 404;


    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }



}
