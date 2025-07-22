package io.github.some_example_name.model;

public class SMS {
    private final String message;
    private boolean isRead;
    private final String sender;
    private final String receiver;
    private final boolean isForMarriage;

    public SMS(String message, boolean isRead, String sender, String receiver, boolean isForMarriage) {
        this.message = message;
        this.isRead = isRead;
        this.sender = sender;
        this.receiver = receiver;
        this.isForMarriage = isForMarriage;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isForMarriage() {
        return isForMarriage;
    }
}
