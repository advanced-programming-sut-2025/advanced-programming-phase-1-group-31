package io.github.some_example_name.controller;

import common.Message;
import io.github.some_example_name.model.GameApp;

import java.util.HashMap;

public class ServerMessageController {
    public static Message handleMessage(Message message){
        if (message.getType().equals(Message.Type.Get_Status)) return getStatus(message);
        return null;
    }

    private static Message getStatus(Message message){
        HashMap<String,Object> body = new HashMap<>();
        body.put("username", GameApp.username);
        return new Message(body, Message.Type.Get_Status);
    }

}

