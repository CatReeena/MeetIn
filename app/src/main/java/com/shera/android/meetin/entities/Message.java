package com.shera.android.meetin.entities;

import org.threeten.bp.LocalDateTime;

import java.util.Objects;
import java.util.UUID;



public class Message {

    private UUID id;
    private Person sender;
    private Person receiver;
    private String message;
    private LocalDateTime dateTime;

    /**
     * Constructs new personal message.
     *
     * @param message text of the message
     * @param sender person who sent the message
     * @param receiver person who will receive the message
     * @param dateTime creation date and time
     */
    public Message(String message, Person sender, Person receiver, LocalDateTime dateTime) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.dateTime = dateTime;
        this.id = UUID.randomUUID();
    }

    protected Message() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }

        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
