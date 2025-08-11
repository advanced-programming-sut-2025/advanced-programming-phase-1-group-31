package common;

public class Chat {
    private final String message;
    private final String sender;
    private final String receiver;

    public Chat(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Chat(String message, String sender) {
        this.message = message;
        this.sender = sender;
        this.receiver = null;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
