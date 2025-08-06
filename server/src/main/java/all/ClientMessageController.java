package all;

import common.Lobby;
import common.Message;
import common.UserInfo;

import java.util.*;
import java.util.stream.Collectors;

public class ClientMessageController {
    public static Message handleMessage(Message message, ClientConnectionThread cct) {
        if (message.getType().equals(Message.Type.Menu)) return parseUsername(message, cct);
        else if (message.getType().equals(Message.Type.Get_Status)) return refreshStatus(message, cct);
        else if (message.getType().equals(Message.Type.All_Players)) return sendAllPlayers();
        else if (message.getType().equals(Message.Type.Get_Lobby_ID)) return generateId();
        else if (message.getType().equals(Message.Type.Get_Lobby)) return saveLobby(message, cct);
        else if (message.getType().equals(Message.Type.All_Lobbies)) return sendAllLobbies(message, cct);
        else if (message.getType().equals(Message.Type.Which_Lobby)) return whichLobbyIsThePlayer(message, cct);

        return null;
    }

    private static Message whichLobbyIsThePlayer(Message message, ClientConnectionThread cct) {
        UserInfo player = message.getFromBody("player", UserInfo.class);
        for (Lobby lobby : ServerApp.lobbies) {
            if (lobby.getPlayers().contains(player.getUsername())) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("lobby", lobby);
                return new Message(body, Message.Type.Menu);
            }
        }
        return null;
    }

    private static Message sendAllLobbies(Message message, ClientConnectionThread cct) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbies", ServerApp.lobbies);
        return new Message(body, Message.Type.Menu);
    }

    private static Message saveLobby(Message message, ClientConnectionThread cct) {
        Lobby incomingLobby = message.getFromBody("lobby", Lobby.class);

        Boolean success = message.getFromBody("delete-lobby", Boolean.class);

        if (success != null) {
            ServerApp.lobbies.removeIf(l -> l.getLobbyID() == incomingLobby.getLobbyID());
            return null;
        }

        ServerApp.lobbies.removeIf(l -> l.getLobbyID() == incomingLobby.getLobbyID());

        ServerApp.lobbies.add(incomingLobby);

        Boolean leave = message.getFromBody("leave", Boolean.class);

        cct.getPlayer().setLobby(incomingLobby);

        for (UserInfo player : ServerApp.players) {
            if (incomingLobby.getPlayers().contains(player.getUsername())) {
                player.setLobby(incomingLobby);
            }
        }

        return null;
    }


    private static Message generateId() {
        Random rand = new Random();
        Set<Integer> existingIds = new HashSet<>();
        if (!ServerApp.lobbies.isEmpty()) {
            existingIds = ServerApp.lobbies.stream()
                .map(Lobby::getLobbyID)
                .collect(Collectors.toSet());
        }

        int id;
        do {
            id = rand.nextInt(10000) + 1;
        } while (existingIds.contains(id));

        HashMap<String, Object> body = new HashMap<>();
        body.put("id", id);
        return new Message(body, Message.Type.Menu);
    }

    private static Message sendAllPlayers() {
        HashMap<String, Boolean> players = new HashMap<>();
        for (UserInfo player : ServerApp.players) {
            boolean isOnline = ServerApp.connections.stream().anyMatch(p -> p.getPlayer() != null && p.getPlayer().getUsername().equals(player.getUsername()));
            players.put(player.getUsername(), isOnline);
        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("players", players);
        return new Message(body, Message.Type.Menu);
    }

    private static Message refreshStatus(Message message, ClientConnectionThread cct) {
        UserInfo player = message.getFromBody("player info", UserInfo.class);
        if (player == null) {
            cct.setPlayer(null);
            return null;
        }
        cct.setPlayer(player);
        System.out.println("refresh infos of " + cct.getPlayer().getUsername() + " " + cct.getTimeToConnect());
        return null;
    }

    private static Message parseUsername(Message message, ClientConnectionThread cct) {
        String command = message.getFromBody("command", String.class);
        if (command.equals("signup")) {
            UserInfo player = message.getFromBody("player", UserInfo.class);
            boolean isExist = ServerApp.players.stream().anyMatch(p -> p.getUsername().equals(player.getUsername()));
            if (isExist) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("error-message", "player already exists");
                return new Message(body, Message.Type.Menu);
            } else {
                ServerApp.players.add(player);
                cct.setPlayer(player);
            }
        } else if (command.equals("login")) {
            String username = message.getFromBody("username", String.class);
            Optional<UserInfo> player = ServerApp.players.stream().filter(p -> p.getUsername().equals(username)).
                findFirst();
            HashMap<String, Object> body = new HashMap<>();
            if (!player.isPresent()) {
                body.put("error-message", "player does not exist");
            } else {
                body.put("player", player.get());
                cct.setPlayer(player.get());
            }
            return new Message(body, Message.Type.Menu);
        }
        return null;
    }
}
