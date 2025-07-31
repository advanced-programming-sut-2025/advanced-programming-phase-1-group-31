package all;

import common.Message;
import common.Player;

import java.util.HashMap;
import java.util.Optional;

public class ClientMessageController {
    public static Message handleMessage(Message message, ClientConnectionThread cct) {
        if (message.getType().equals(Message.Type.Menu)) return parseUsername(message, cct);
        if (message.getType().equals(Message.Type.Get_Status)) return refreshStatus(message, cct);
        return null;
    }

    private static Message refreshStatus(Message message, ClientConnectionThread cct) {
        Player player = message.getFromBody("player info",  Player.class);
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
            Player player = message.getFromBody("player", Player.class);
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
            Optional<Player> player = ServerApp.players.stream().filter(p -> p.getUsername().equals(username)).
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
