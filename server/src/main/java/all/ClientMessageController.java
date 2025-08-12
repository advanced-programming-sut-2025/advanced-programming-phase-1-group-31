package all;

import common.*;

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
        else if (message.getType().equals(Message.Type.Get_Reaction)) return getAndSaveReaction(message, cct);
        else if (message.getType().equals(Message.Type.Get_Message)) return getChatsOrSave(message, cct);
        else if (message.getType().equals(Message.Type.Get_Player_Board)) return getPlayerBoardAndSave(message, cct);
        else if (message.getType().equals(Message.Type.Send_Somebody_Out)) return sendSomebodyOut(message, cct);
        else if (message.getType().equals(Message.Type.Terminate_Game_Request))
            return terminateGameRequest(message, cct);


        return null;
    }

    private static final ArrayList<Boolean> sendOutVote = new ArrayList<>();

    private static Message sendSomebodyOut(Message message, ClientConnectionThread cct) {
        Lobby lobby = cct.getPlayer().getLobby();
        String username = message.getFromBody("username", String.class);
        Boolean vote = message.getFromBody("vote", Boolean.class);
        if (vote != null) {
            sendOutVote.add(vote);
        }
        if (sendOutVote.isEmpty()) {
            sendOutVote.add(false);
            for (String player : lobby.getPlayers()) {
                if (!player.equals(username)) {
                    ClientConnectionThread c = ServerApp.getConnectionByUsername(player);
                    if (c != null) {
                        c.sendMessage(message);
                    }
                }
            }

        } else if (sendOutVote.size() == lobby.getNumberOfPlayers()) {
            int agreement = 0;
            for (Boolean bool : sendOutVote) {
                if (bool) {
                    agreement += 1;
                }
            }
            boolean sendOut = agreement > (lobby.getNumberOfPlayers() / 2);
            if  (sendOut) {
                ClientConnectionThread c = ServerApp.getConnectionByUsername(username);
                assert c != null;
                c.sendMessage(new Message(new HashMap<>(), Message.Type.ByBy));
                c.getPlayer().setLobby(null);
                lobby.removePlayer(username);
                if (username.equals(lobby.getAdmin())) lobby.setAdmin(lobby.getPlayers().getFirst());
                for (String player : lobby.getPlayers()) {
                    c = ServerApp.getConnectionByUsername(player);
                    if (c != null) {
                        HashMap<String, Object> body = new HashMap<>();
                        body.put("lobby", lobby);
                        c.sendMessage(new Message(body, Message.Type.Get_Lobby));
                    }
                }
            } else {
                for (String player : lobby.getPlayers()) {
                    ClientConnectionThread c = ServerApp.getConnectionByUsername(player);
                    if (c != null) {
                        HashMap<String, Object> body = new HashMap<>();
                        body.put("terminate", false);
                        c.sendMessage(new Message(body, Message.Type.Terminate_Game));
                    }
                }

            }
            sendOutVote.clear();
        }

        return null;
    }

    private static final ArrayList<Boolean> terminateVotes = new ArrayList<>();

    private static Message terminateGameRequest(Message message, ClientConnectionThread cct) {
        Lobby lobby = cct.getPlayer().getLobby();
        Boolean vote = message.getFromBody("vote", Boolean.class);
        if (vote != null) {
            terminateVotes.add(vote);
        }
        if (terminateVotes.isEmpty()) {
            terminateVotes.add(true);
            for (String player : lobby.getPlayers()) {
                if (!cct.getPlayer().getUsername().equals(player)) {
                    ClientConnectionThread c = ServerApp.getConnectionByUsername(player);
                    if (c != null) {
                        c.sendMessage(new Message(new HashMap<>(), Message.Type.Terminate_Game_Request));
                    }
                }
            }

        } else if (terminateVotes.size() == lobby.getNumberOfPlayers()) {
            int agreement = 0;
            for (Boolean bool : terminateVotes) {
                if (bool) {
                    agreement += 1;
                }
            }
            Boolean terminated = agreement > (lobby.getNumberOfPlayers() / 2);
            for (String player : lobby.getPlayers()) {
                ClientConnectionThread c = ServerApp.getConnectionByUsername(player);
                if (c != null) {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("terminate", terminated);
                    c.sendMessage(new Message(body, Message.Type.Terminate_Game));
                    if (terminated) c.getPlayer().setLobby(null);
                }
            }
            terminateVotes.clear();
        }

        return null;
    }

    private static Message getPlayerBoardAndSave(Message message, ClientConnectionThread cct) {
        Lobby lobby = cct.getPlayer().getLobby();
        if (message.getBody().isEmpty()) {
            ArrayList<PlayerBoard> playerBoards = lobby.getPlayers().stream().map(p -> Objects.requireNonNull(ServerApp.getConnectionByUsername(p)).getPlayerBoard()).collect(Collectors.toCollection(ArrayList::new));
            HashMap<String, Object> body = new HashMap<>();
            body.put("player-boards", playerBoards);
            return new Message(body, Message.Type.Menu);
        } else {
            PlayerBoard playerBoard = message.getFromBody("player-board", PlayerBoard.class);
            cct.setPlayerBoard(playerBoard);
            return null;
        }

    }

    private static Message getChatsOrSave(Message message, ClientConnectionThread cct) {
        Lobby lobby = cct.getPlayer().getLobby();
        if (message.getBody() == null) {
            HashMap<String, Object> body = new HashMap<>();
            body.put("chats", lobby.chats);
            return new Message(body, Message.Type.Menu);
        } else {
            lobby.chats.add(message.getFromBody("chat", Chat.class));
            return null;
        }
    }

    private static Message getAndSaveReaction(Message message, ClientConnectionThread cct) {
        ArrayList<String> username = cct.getPlayer().getLobby().getPlayers();
        for (String user : username) {
            ClientConnectionThread clientConnectionThread = ServerApp.getConnectionByUsername(user);
            if (clientConnectionThread != null) {
                clientConnectionThread.sendMessage(message);
            }

        }
        return null;
    }

    private static Message getPlaceAndSave(Message message, ClientConnectionThread cct) {

        int x = message.getIntFromBody("x");
        int y = message.getIntFromBody("y");
        cct.setOther(new Others(cct.getOther().getNumber(), new Point(x, y)));

        String currentUsername = cct.getPlayer().getUsername();
        Lobby lobby = cct.getPlayer().getLobby();

        List<Others> others = lobby.getPlayers().stream().filter(username -> !username.equals(currentUsername)).map(ServerApp::getConnectionByUsername).filter(Objects::nonNull).map(ClientConnectionThread::getOther).toList();

        HashMap<String, Object> body = new HashMap<>();
        body.put("others", others);

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
                    int number = message1.getFromBody("number", Integer.class);
                    cct.setOther(new Others(number, new Point(0, 0)));
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("number", number);
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
            Boolean isOnline = ServerApp.connections.stream().anyMatch(p -> p.getPlayer() != null && p.getPlayer().getUsername().equals(player.getUsername()));
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
        return null;
    }

    private static Message parseUsername(Message message, ClientConnectionThread cct) {
        String command = message.getFromBody("command", String.class);
        if (command.equals("signup")) {
            UserInfo player = message.getFromBody("player", UserInfo.class);
            Boolean isExist = ServerApp.players.stream().anyMatch(p -> p.getUsername().equals(player.getUsername()));
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
