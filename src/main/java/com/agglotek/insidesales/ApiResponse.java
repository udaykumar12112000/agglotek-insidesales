package com.agglotek.insidesales;

public class ApiResponse {
    private boolean status;
    private String message;
    private Object object;

    public ApiResponse() {}

    public ApiResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(boolean status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
