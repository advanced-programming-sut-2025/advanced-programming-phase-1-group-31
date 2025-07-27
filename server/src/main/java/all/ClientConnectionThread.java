package all;

import common.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnectionThread extends Thread {
    public static final int TIMEOUT_MILLIS = 15000;

    private final AtomicBoolean isEnd;
    private final AtomicBoolean reconnectTimedOut;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private Player player = new Player("m", "m", "m");
    private final LocalTime timeToConnect;

    @Override
    public void run() {
        try {
            refreshStatus();
            ServerApp.addConnection(this);
            while (!isEnd.get()) {
                try {
                    String receivedStr = dataInputStream.readUTF();
                    Message message = JSON.fromJson(receivedStr);
                    Message generatedMessage = ClientMessageController.handleMessage(message, this);
                    if (generatedMessage != null) sendMessage(generatedMessage);
                    checkReconnection();
                } catch (Exception e) {
                    System.err.println(player.getUsername() + " disconnected.");
                    disconnectingCountDown();
                    break;
                }
            }
            end(" has been shut down", false);

        } catch (Exception ignore) {
        }
    }


    // Constructor
    public ClientConnectionThread(Socket socket) throws IOException {
        isEnd = new AtomicBoolean(false);
        reconnectTimedOut = new AtomicBoolean(false);
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.timeToConnect = LocalTime.now();
    }


    // Methods

    private void checkReconnection() {
        ArrayList<ClientConnectionThread> connections = ServerApp.connections;
        for (ClientConnectionThread cct : connections) {
            if (!this.equals(cct) && player.getUsername() != null && player.getUsername().equals(cct.player.getUsername()) && cct.timeToConnect.isBefore(this.timeToConnect)) {
                cct.end(" reconnected", true);
            }
        }
    }

    private void disconnectingCountDown() {
        new Thread(() -> {
            try {
                Thread.sleep(TIMEOUT_MILLIS);
                reconnectTimedOut.set(true);
                if (!isEnd.get()) {
                    end(" don't reconnect in time. Closing...", false);
                }
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    private synchronized void sendMessage(Message message) {
        String jsonMessage = JSON.toJson(message);
        try {
            dataOutputStream.writeUTF(jsonMessage);
        } catch (Exception e) {
            System.out.println("Can't send message: " + e.getMessage());
        }
    }

    public void refreshStatus() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("request", "player info");
        sendMessage(new Message(body, Message.Type.Get_Status));
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isEnd() {
        return isEnd.get();
    }

    public Player getPlayer() {
        return player;
    }

    public String getTimeToConnect() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timeToConnect.format(formatter);
    }

    public void setPlayer(Player player) {
        this.player =  player;
    }

    public synchronized void end(String message, boolean reconnecting) {
        if (!reconnecting) {
            if (isEnd.get() || !reconnectTimedOut.get()) return;
        }
        isEnd.set(true);
        try {
            //TODO: When a client ends what should be happen?? all lobbies have to be deleted
            System.out.println(player.getUsername() + message);
            ServerApp.removeConnection(this);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
