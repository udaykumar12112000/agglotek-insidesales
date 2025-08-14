package com.agglotek.insidesales.service.impl;

public class NotificationDTO {

    private String message;
    private String priority;

    public NotificationDTO(String message, String priority) {
        this.message = message;
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public String getPriority() {
        return priority;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
