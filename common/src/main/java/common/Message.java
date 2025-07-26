package common;

import java.util.HashMap;


public class Message {
    private Type type;
    private HashMap<String, Object> body;

    /*
     * Empty constructor needed for common.JSON Serialization/Deserialization
     */
    public Message() {
    }

    public Message(HashMap<String, Object> body, Type type) {
        this.body = body;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        Get_Status,
        Menu,
        Heartbeat,
        Error,





    }

    /*/ Messages Body
        1) request
        2) username
        3) message
        4) user-existence

     */
    @Override
    public String toString() {
        return type.toString() + ": " + body.toString();
    }
}
