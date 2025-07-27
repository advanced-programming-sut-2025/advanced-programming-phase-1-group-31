package all;

import common.*;

import java.io.IOException;
import java.util.ArrayList;

public class ServerApp {
    private static ListenerThread listenerThread;
    public static final ArrayList<ClientConnectionThread> connections =  new ArrayList<>();
    public static ArrayList<Player> players =  new ArrayList<>();


    public static ClientConnectionThread getConnectionByUsername(String username) {
        for (ClientConnectionThread connectionThread : connections) {
            if (connectionThread.getPlayer().getUsername().equals(username))
                return connectionThread;
        }
        return null;
    }

    public static void setListenerThread(ListenerThread listener) {
        listenerThread = listener;
    }

    public static void startListening() {
        if (listenerThread != null) {
            listenerThread.start();
        } else {
            System.err.println("all.ListenerThread is not set!");
        }
    }

    public static void addConnection(ClientConnectionThread connection) {
        if (!connection.isEnd()) {
            connections.add(connection);
        }
    }

    public static void removeConnection(ClientConnectionThread connection) {
        try {
            connection.getSocket().close();
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }

        connections.remove(connection);
    }

    public static synchronized void end(){
        for (ClientConnectionThread connectionThread : connections) {
            connectionThread.end("'s Server disconnected", true);
        }
        listenerThread.interrupt();
        SaveLoadPlayers.savePlayers(players);
        connections.clear();
        players.clear();
    }
}

