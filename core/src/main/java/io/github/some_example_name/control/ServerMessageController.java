package io.github.some_example_name.control;

import common.Message;
import io.github.some_example_name.model.GameApp;

import java.util.HashMap;

public class ServerMessageController {
    public static Message handleMessage(Message message){
        if (message.getType().equals(Message.Type.Get_Status)) return getStatus();
        return null;
    }

    private static Message getStatus(){
        HashMap<String,Object> body = new HashMap<>();
        body.put("player info", GameApp.player);
        return new Message(body, Message.Type.Get_Status);
    }

}

