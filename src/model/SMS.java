package model;

public class SMS {
    private final String message;
    private boolean isRead;
    private final String sender;
    private final String receiver;

    public SMS(String message, boolean isRead, String sender, String receiver) {
        this.message = message;
        this.isRead = isRead;
        this.sender = sender;
        this.receiver = receiver;
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
}
