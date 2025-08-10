package common;

import com.google.gson.Gson;

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

    public HashMap<String, Object> getBody() {
        return body;
    }

    public Type getType() {
        return type;
    }

    public <T> T getFromBody(String fieldName, Class<T> clazz) {
        Object value = body.get(fieldName);
        if (value == null) return null;
        if (clazz.isInstance(value)) return clazz.cast(value);

        Gson gson = new Gson();
        String json = gson.toJson(value);
        return gson.fromJson(json, clazz);
    }

    public <T> T getFromBodyType(String fieldName, java.lang.reflect.Type type) {
        Object value = body.get(fieldName);
        if (value == null) return null;

        Gson gson = new Gson();
        String json = gson.toJson(value);
        return gson.fromJson(json, type);
    }


    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        Get_Status,
        Menu,
        Error,
        All_Players,
        Get_Lobby_ID,
        Get_Lobby,
        All_Lobbies,
        Which_Lobby,
        Start_Button_Pressed,
        Players_Map,
        Start_Game,
        Get_Place;



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
