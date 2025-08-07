package io.github.some_example_name.control;

import com.badlogic.gdx.Gdx;
import common.Lobby;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.PreGameMenuView;

import java.util.HashMap;

public class ServerMessageController {
    public static Message handleMessage(Message message){
        if (message.getType().equals(Message.Type.Get_Status)) return getStatus();
        else if (message.getType().equals(Message.Type.Start_Button_Pressed)) return startGame(message);
        return null;
    }

    private static Message startGame(Message message) {
        Lobby lobby = message.getFromBody("lobby", Lobby.class);
        int number = message.getFromBody("number", Integer.class);
            Gdx.app.postRunnable(() -> Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(GameAssetManager.getGameAssetManager().getSkin()), GameAssetManager.getGameAssetManager().getSkin(), lobby, number)));
        return null;
    }

    private static Message getStatus(){
        HashMap<String,Object> body = new HashMap<>();
        body.put("player info", GameApp.player.getUserInfo());
        return new Message(body, Message.Type.Get_Status);
    }

}

