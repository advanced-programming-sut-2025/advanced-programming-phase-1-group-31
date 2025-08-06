package io.github.some_example_name.model;

import common.Lobby;
import common.Message;
import common.Player;

import java.util.HashMap;

public class GameApp {
    public static C2SConnectionThread c2sConnectionThread;
    public static Player player;

    public static void startGame(Lobby lobby) {

    }

    public static void leaveLobby(Lobby lobby) {
        lobby.removePlayer(player.getUsername());
        HashMap<String, Object> body = new HashMap<>();
        if (lobby.getAdmin().equals(player.getUsername()) && lobby.getNumberOfPlayers() != 0) {
            lobby.setAdmin(lobby.getPlayers().getFirst());
        } else if (lobby.getNumberOfPlayers() == 0) {
            body.put("delete-lobby", true);
        }
        body.put("lobby", lobby);
        c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Lobby));
        player.setLobby(null);
    }
}
