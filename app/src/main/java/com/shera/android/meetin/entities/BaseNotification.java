package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.LocalDateTime;

import java.io.Serializable;


public class BaseNotification implements Serializable {

    private Long id;
    @JsonProperty("sent")
    private LocalDateTime dateTime;
    private String message;
    private Project project;
    private Person receiver;
    @JsonProperty("type")
    protected NotificationType notificationType;

    public BaseNotification() {
        if (notificationType == null) {
            notificationType = NotificationType.BASE;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

}
