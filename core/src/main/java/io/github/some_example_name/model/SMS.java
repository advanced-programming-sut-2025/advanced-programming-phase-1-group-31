package io.github.some_example_name.model;

public class SMS {
    private final String message;
    private Boolean isRead;
    private final String sender;
    private final String receiver;
    private final Boolean isForMarriage;

    public SMS(String message, Boolean isRead, String sender, String receiver, Boolean isForMarriage) {
        this.message = message;
        this.isRead = isRead;
        this.sender = sender;
        this.receiver = receiver;
        this.isForMarriage = isForMarriage;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isRead() {
        return isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Boolean isForMarriage() {
        return isForMarriage;
    }
}
