import common.Message;

public class ClientMessageController {
    public static Message handleMessage(Message message, ClientConnectionThread cct) {
        if (message.getType().equals(Message.Type.Get_Status)) return parseUsername(message, cct);
        if (message.getType().equals(Message.Type.Heartbeat)) return heartbeat(cct);
        return null;
    }

    private static Message heartbeat(ClientConnectionThread cct) {
        System.out.println("heartbeat " + cct.getUsername() + cct.getTimeToConnect());
        return null;
    }

    private static Message parseUsername(Message message, ClientConnectionThread cct) {
        String username = message.getFromBody("username");
        cct.setUsername(username);
        return null;
    }
}
