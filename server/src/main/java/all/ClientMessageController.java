package all;

import common.Lobby;
import common.Message;
import common.UserInfo;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMessageController {
    public static Message handleMessage(Message message, ClientConnectionThread cct) {
        if (message.getType().equals(Message.Type.Menu)) return parseUsername(message, cct);
        else if (message.getType().equals(Message.Type.Get_Status)) return refreshStatus(message, cct);
        else if (message.getType().equals(Message.Type.All_Players)) return sendAllPlayers();
        else if (message.getType().equals(Message.Type.Get_Lobby_ID)) return generateId();
        else if (message.getType().equals(Message.Type.Get_Lobby)) return saveLobby(message, cct);
        else if (message.getType().equals(Message.Type.All_Lobbies)) return sendAllLobbies();
        else if (message.getType().equals(Message.Type.Which_Lobby)) return whichLobbyIsThePlayer(message);
        else if (message.getType().equals(Message.Type.Start_Button_Pressed)) return startAnnouncement(message);
        else if (message.getType().equals(Message.Type.Players_Map)) return sendOthersMap(message);
        else if (message.getType().equals(Message.Type.Get_Place)) return getPlaceAndSave(message, cct);


        return null;
    }

    private static Message getPlaceAndSave(Message message, ClientConnectionThread cct) {
        int x = message.getIntFromBody("x");
        int y = message.getIntFromBody("y");
        cct.setPoint(new Point(x, y));

        String currentUsername = cct.getPlayer().getUsername();
        Lobby lobby = cct.getPlayer().getLobby();

        List<Point> points = lobby.getPlayers().stream()
            .filter(username -> !username.equals(currentUsername))
            .map(ServerApp::getConnectionByUsername)
            .filter(Objects::nonNull)
            .map(ClientConnectionThread::getPoint)
            .toList();

        HashMap<String, Object> body = new HashMap<>();
        body.put("points",  points);
        return new Message(body, Message.Type.Get_Place);
    }


    private static final ArrayList<Message> messagesOfMap = new ArrayList<>();

    private static Message sendOthersMap(Message message) {
        Lobby lobby = message.getFromBody("lobby", Lobby.class);
        messagesOfMap.add(message);
        if (messagesOfMap.size() == lobby.getNumberOfPlayers()) {
            HashMap<Integer, String> mapNumber = new HashMap<>();
            for (int i = 0; i < messagesOfMap.size(); i++) {
                Message message1 = messagesOfMap.get(i);
                message1.getBody().put("number", i);
                String selectedMap = message1.getFromBody("selected-map", String.class);
                mapNumber.put(i, selectedMap);
            }
            for (Message message1 : messagesOfMap) {
                String username = message1.getFromBody("username", String.class);
                ClientConnectionThread cct = ServerApp.getConnectionByUsername(username);
                if (cct != null) {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("number", message1.getFromBody("number", Integer.class));
                    body.put("all-maps", mapNumber);
                    cct.sendMessage(new Message(body, Message.Type.Start_Game));
                }
            }

            messagesOfMap.clear();
        }
        return null;
    }

    private static Message startAnnouncement(Message message) {
        Lobby lobby = message.getFromBody("lobby", Lobby.class);
        ArrayList<ClientConnectionThread> CCTs = new ArrayList<>();
        for (String username : lobby.getPlayers()) {
            if (!username.equals(lobby.getAdmin())) {
                ClientConnectionThread cct = ServerApp.getConnectionByUsername(username);
                if (cct != null) {
                    CCTs.add(cct);
                } else {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("error-message", "One or more of the players\nhave not been online yet");
                    return new Message(body, Message.Type.Menu);
                }
            }
        }
        for (ClientConnectionThread cct : CCTs) {
            HashMap<String, Object> body = new HashMap<>();
            body.put("lobby", lobby);
            cct.sendMessage(new Message(body, Message.Type.Start_Button_Pressed));
        }
        return new Message(new HashMap<>(), Message.Type.Menu);
    }

    private static Message whichLobbyIsThePlayer(Message message) {
        UserInfo player = message.getFromBody("player", UserInfo.class);
        for (UserInfo userInfo : ServerApp.players) {
            Lobby lobby = userInfo.getLobby();
            if (lobby != null && lobby.getPlayers().contains(player.getUsername())) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("lobby", lobby);
                return new Message(body, Message.Type.Menu);
            }
        }
        return null;
    }

    private static Message sendAllLobbies() {
        Set<Integer> seenIds = new HashSet<>();
        List<Lobby> uniqueLobbies = new ArrayList<>();

        for (UserInfo userInfo : ServerApp.players) {
            Lobby lobby = userInfo.getLobby();
            if (lobby != null && seenIds.add(lobby.getLobbyID())) {
                uniqueLobbies.add(lobby);
            }
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbies", uniqueLobbies);
        return new Message(body, Message.Type.Menu);
    }

    private static Message saveLobby(Message message, ClientConnectionThread cct) {
        Lobby incomingLobby = message.getFromBody("lobby", Lobby.class);
        Boolean deleteRequested = message.getFromBody("delete-lobby", Boolean.class);

        ArrayList<String> players = new ArrayList<>();

        if (deleteRequested != null && deleteRequested) {
            Lobby currentLobby = cct.getPlayer().getLobby();
            if (currentLobby != null) {
                players.addAll(currentLobby.getPlayers());
            }
            incomingLobby = null;
        } else if (incomingLobby != null) {
            players.addAll(incomingLobby.getPlayers());
        }

        cct.getPlayer().setLobby(incomingLobby);

        for (UserInfo player : ServerApp.players) {
            if (players.contains(player.getUsername())) {
                player.setLobby(incomingLobby);
            }
        }

        return null;
    }

    private static Message generateId() {
        Random rand = new Random();
        Set<Integer> existingIds = new HashSet<>();
        ArrayList<Lobby> lobbies = new ArrayList<>();
        for (UserInfo userInfo : ServerApp.players) {
            Lobby lobby = userInfo.getLobby();
            if (lobby != null && lobbies.stream().noneMatch(l -> l.getLobbyID() == lobby.getLobbyID())) {
                lobbies.add(userInfo.getLobby());
            }
        }
        if (!lobbies.isEmpty()) {
            existingIds = lobbies.stream().map(Lobby::getLobbyID).collect(Collectors.toSet());
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
            Optional<UserInfo> player = ServerApp.players.stream().filter(p -> p.getUsername().equals(username)).findFirst();
            HashMap<String, Object> body = new HashMap<>();
            if (player.isEmpty()) {
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
