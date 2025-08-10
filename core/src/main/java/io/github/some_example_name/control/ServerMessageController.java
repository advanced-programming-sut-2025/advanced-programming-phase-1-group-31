package io.github.some_example_name.control;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.Lobby;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.PreGameMenuView;
import io.github.some_example_name.view.PrePreGameMenuView;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerMessageController {
    public static Message handleMessage(Message message) {
        if (message.getType().equals(Message.Type.Get_Status)) return getStatus();
        else if (message.getType().equals(Message.Type.Start_Button_Pressed)) return startGame(message);
        else if (message.getType().equals(Message.Type.Start_Game)) return startStartGame(message);
        else if (message.getType().equals(Message.Type.Get_Place)) return setPlaces(message);
        return null;
    }

    private static Message setPlaces(Message message) {
        Type type = new TypeToken<ArrayList<Point>>() {
        }.getType();
        ArrayList<Point> points = message.getFromBodyType("points", type);
        if (points.isEmpty()) return null;
        GameApp.othersPoint.clear();
        GameApp.othersPoint.addAll(points);
        return null;
    }

    private static Message startStartGame(Message message) {
        Gdx.app.postRunnable(() -> Main.getMain().setScreen(new PrePreGameMenuView(message)));
        return null;
    }

    private static Message startGame(Message message) {
        Lobby lobby = message.getFromBody("lobby", Lobby.class);
        Gdx.app.postRunnable(() -> Main.getMain().setScreen(new PreGameMenuView(GameAssetManager.getGameAssetManager().getSkin(), lobby)));
        return null;
    }

    private static Message getStatus() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("player info", GameApp.player.getUserInfo());
        return new Message(body, Message.Type.Get_Status);
    }

}

