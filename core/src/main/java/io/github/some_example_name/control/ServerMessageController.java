package io.github.some_example_name.control;

import com.badlogic.gdx.Gdx;
import com.google.gson.reflect.TypeToken;
import common.Lobby;
import common.Message;
import common.Others;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Reactions;
import io.github.some_example_name.view.MainMenuView;
import io.github.some_example_name.view.PreGameMenuView;
import io.github.some_example_name.view.PrePreGameMenuView;
import io.github.some_example_name.view.VotingMenuView;

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
        else if (message.getType().equals(Message.Type.Get_Reaction)) return reaction(message);
        else if (message.getType().equals(Message.Type.Terminate_Game_Request)) return terminateVoting();
        else if (message.getType().equals(Message.Type.Terminate_Game)) return terminate(message);
        else if (message.getType().equals(Message.Type.Send_Somebody_Out)) return sendSomebodyOut(message);
        else if (message.getType().equals(Message.Type.Get_Lobby)) return getAndSaveLobby(message);
        else if (message.getType().equals(Message.Type.ByBy)) return byBy();
        return null;
    }

    private static Message getAndSaveLobby(Message message) {
        Lobby lobby = message.getFromBody("lobby", Lobby.class);
        GameApp.player.setLobby(lobby);
        GameApp.others.clear();
        GameApp.others.add(new Others(0, new Point(0, 0)));
        GameApp.others.add(new Others(0, new Point(0, 0)));
        GameApp.others.add(new Others(0, new Point(0, 0)));
        Gdx.app.postRunnable(() -> Main.getMain().setScreen(GameApp.getGameView()));
        return null;
    }

    private static Message byBy() {
        Gdx.app.postRunnable(() -> Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin())));
        GameApp.player.setLobby(null);
        GameApp.setMapManager(null);
        return null;
    }

    private static Message terminate(Message message) {
        Boolean terminated = message.getFromBody("terminate", Boolean.class);
        if (terminated) {
            GameApp.player.setLobby(null);
            Gdx.app.postRunnable(() -> Main.getMain().setScreen(new MainMenuView(GameAssetManager.getGameAssetManager().getSkin())));
            GameApp.setMapManager(null);
        } else {
            Gdx.app.postRunnable(() -> Main.getMain().setScreen(GameApp.getGameView()));
        }
        return null;
    }

    private static Message sendSomebodyOut(Message message) {
        String username = message.getFromBody("username", String.class);
        Gdx.app.postRunnable(() -> Main.getMain().setScreen(new VotingMenuView(GameAssetManager.getGameAssetManager().getSkin(), false, username)));
        return null;
    }

    private static Message terminateVoting() {
        Gdx.app.postRunnable(() -> Main.getMain().setScreen(new VotingMenuView(GameAssetManager.getGameAssetManager().getSkin(), true, null)));
        return null;
    }

    private static Message reaction(Message message) {
        Reactions reaction = message.getFromBody("reaction", Reactions.class);
        reaction.setTime(System.currentTimeMillis());
        GameApp.reactions.add(reaction);
        return null;
    }

    private static Message setPlaces(Message message) {
        Type type = new TypeToken<ArrayList<Others>>() {
        }.getType();
        ArrayList<Others> others = message.getFromBodyType("others", type);
        if (others.isEmpty()) return null;
        GameApp.others.clear();
        GameApp.others.addAll(others);
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

