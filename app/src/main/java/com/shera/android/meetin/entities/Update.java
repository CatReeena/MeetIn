package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.LocalDateTime;

import java.io.Serializable;

public class Update implements Serializable {

    private Long id;
    private Project project;
    @JsonProperty("posted")
    private LocalDateTime dateTime;
    private String message;
    private String title;
    @JsonProperty("short_message")
    private String shortMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }
}
