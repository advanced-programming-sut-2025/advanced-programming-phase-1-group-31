package all;

import common.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnectionThread extends Thread {
    public static final int TIMEOUT_MILLIS = 15000;

    private final AtomicBoolean isEnd;
    private final AtomicBoolean isPlayerConnected;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private UserInfo player;
    private Others other = new Others(0, new Point(0, 0));
    private PlayerBoard playerBoard = new PlayerBoard("", 0, 0, 0, 0, 0);
    private final LocalTime timeToConnect;
    private Thread counterThread;

    @Override
    public void run() {
        try {
            ServerApp.addConnection(this);
            while (!isEnd.get()) {
                try {
                    String receivedStr = dataInputStream.readUTF();
                    Message message = JSON.fromJson(receivedStr);
                    Message generatedMessage = ClientMessageController.handleMessage(message, this);
                    if (generatedMessage != null) sendMessage(generatedMessage);
                    if (player != null) {
                        isPlayerConnected.set(true);
                        if (counterThread != null) {
                            counterThread.interrupt();
                            System.out.println(player.getUsername() + " reconnected");
                            counterThread = null;
                        }
                    }
                    if (player == null && isPlayerConnected.get()) {
                        isPlayerConnected.set(false);
                        System.err.println("Player disconnected");
                        disconnectingCountDown();
                    }
                    checkReconnection();
                } catch (Exception e) {
                    String name = "Player";
                    if (player != null) name = player.getUsername();
                    System.err.println(name + " disconnected.");
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
        isPlayerConnected = new AtomicBoolean(false);
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.timeToConnect = LocalTime.now();
    }


    // Methods
    private void checkReconnection() {
        ArrayList<ClientConnectionThread> connections = ServerApp.connections;
        for (ClientConnectionThread cct : connections) {
            if (cct.player != null && this.player != null) {
                if (!this.equals(cct) && player.getUsername() != null && player.getUsername().equals(cct.player.getUsername()) && cct.timeToConnect.isBefore(this.timeToConnect)) {
                    cct.end(" reconnected", true);
                }
            }
        }
    }


    private void disconnectingCountDown() {
        if (counterThread == null) {
            counterThread = new Thread(() -> {
                try {
                    Thread.sleep(TIMEOUT_MILLIS);
                    isPlayerConnected.set(false);
                    ifDidntConnectAgain();
                    String name = "Player";
                    if (player != null) {
                        name = this.player.getUsername();
                    }
                    System.out.println(name + " didn't reconnect. All Lobbies will be disconnected...");
                    counterThread.interrupt();
                } catch (InterruptedException ignored) {
                }
            });
            counterThread.start();
        }
    }

    public synchronized void sendMessage(Message message) {
        String jsonMessage = JSON.toJson(message);
        try {
            dataOutputStream.writeUTF(jsonMessage);
        } catch (Exception e) {
            System.out.println("Can't send message: " + e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Boolean isEnd() {
        return isEnd.get();
    }

    public UserInfo getPlayer() {
        return player;
    }

    public String getTimeToConnect() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timeToConnect.format(formatter);
    }

    public void setPlayer(UserInfo player) {
        this.player = player;
    }

    public Others getOther() {
        return other;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setOther(Others other) {
        this.other = other;
    }

    public synchronized void ifDidntConnectAgain() {

        // TODO: ...

    }

    public synchronized void end(String message, Boolean reconnecting) {
        if (!reconnecting) {
            if (isEnd.get()) return;
        }
        isEnd.set(true);
        try {
            ifDidntConnectAgain();
            String name = "Player";
            if (player != null) {
                name = this.player.getUsername();
            }
            System.out.println(name + message);
            ServerApp.removeConnection(this);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
