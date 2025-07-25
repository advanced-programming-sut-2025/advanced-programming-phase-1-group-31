import java.io.IOException;
import java.util.ArrayList;

public class ServerApp {

    private static ListenerThread listenerThread;
    public static ArrayList<ClientConnectionThread> connections =  new ArrayList<>();


    public static ClientConnectionThread getConnectionByUsername(String username) {
        for (ClientConnectionThread connectionThread : connections) {
            if (connectionThread.getUsername().equals(username))
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
            System.err.println("ListenerThread is not set!");
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
}

